package com.leverx.lms.learningmanagementsystem.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import static com.leverx.lms.learningmanagementsystem.base.enums.UserRole.MANAGER;
import static com.leverx.lms.learningmanagementsystem.base.enums.UserRole.STUDENT;

@Configuration
public class InMemoryUsersConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User
                .withUsername(STUDENT.getDescription())
                .password(passwordEncoder.encode("student123"))
                .roles(STUDENT.getDescription())
                .build();

        UserDetails manager = User
                .withUsername(MANAGER.getDescription())
                .password(passwordEncoder.encode("manager123"))
                .roles(MANAGER.getDescription())
                .build();

        return new InMemoryUserDetailsManager(user, manager);
    }
}
