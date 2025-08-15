package com.example.ecommerce.config;

import com.example.ecommerce.service.DbUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(DbUserDetailsService userDetailsService, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/auth/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/auth/login").defaultSuccessUrl("/", true).permitAll()
            .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login?logout").permitAll();

        // H2 console
        http.headers().frameOptions().sameOrigin();
        http.csrf().ignoringAntMatchers("/h2-console/**");

        return http.build();
    }
}
