package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DbUserDetailsService implements UserDetailsService {
    private final UserRepo users;

    public DbUserDetailsService(UserRepo users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user"));
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPasswordHash(),
                Collections.singleton(new SimpleGrantedAuthority(u.getRole()))
        );
    }
}
