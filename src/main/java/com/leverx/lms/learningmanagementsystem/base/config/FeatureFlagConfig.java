package com.leverx.lms.learningmanagementsystem.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sap.ffs")
public class FeatureFlagConfig {

    private String uri;
    private String username;
    private String password;
}
