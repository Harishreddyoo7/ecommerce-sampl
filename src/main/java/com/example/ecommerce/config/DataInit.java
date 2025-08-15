package com.example.ecommerce.config;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner init(ProductRepo products, UserRepo users, PasswordEncoder encoder) {
        return args -> {
            if (products.count() == 0) {
                products.save(new Product("Wireless Mouse", "Ergonomic 2.4G mouse", new BigDecimal("799.00")));
                products.save(new Product("Mechanical Keyboard", "Blue switch keyboard", new BigDecimal("2999.00")));
                products.save(new Product("USB-C Hub", "7-in-1 multiport adapter", new BigDecimal("1499.00")));
            }
            if (users.findByEmail("admin@example.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setPasswordHash(encoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                users.save(admin);
            }
            if (users.findByEmail("user@example.com").isEmpty()) {
                User u = new User();
                u.setEmail("user@example.com");
                u.setPasswordHash(encoder.encode("user123"));
                users.save(u);
            }
        };
    }
}
