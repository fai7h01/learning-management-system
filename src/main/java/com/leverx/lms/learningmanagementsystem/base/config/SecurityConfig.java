package com.leverx.lms.learningmanagementsystem.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.leverx.lms.learningmanagementsystem.base.enums.UserRole.MANAGER;
import static com.leverx.lms.learningmanagementsystem.base.enums.UserRole.USER;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder.encode("user123"))
                .roles(USER.getDescription())
                .build();

        UserDetails manager = User
                .withUsername("manager")
                .password(passwordEncoder.encode("manager123"))
                .roles(MANAGER.getDescription())
                .build();

        return new InMemoryUserDetailsManager(user, manager);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").hasRole(MANAGER.getDescription())
                        .anyRequest().hasRole(USER.getDescription())
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
