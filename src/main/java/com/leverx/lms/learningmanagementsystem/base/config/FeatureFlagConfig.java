package com.leverx.lms.learningmanagementsystem.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("cloud")
@Data
@Configuration
@ConfigurationProperties(prefix = "sap.ffs")
public class FeatureFlagConfig {

    private String uri;
    private String username;
    private String password;
}
