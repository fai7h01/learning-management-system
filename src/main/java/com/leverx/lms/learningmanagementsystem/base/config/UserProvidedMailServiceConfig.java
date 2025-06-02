package com.leverx.lms.learningmanagementsystem.base.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "mail-service")
public class UserProvidedMailServiceConfig {

    private String host;
    private String port;
    private String username;
    private String password;

    @Bean
    public JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }
}
