package com.leverx.lms.learningmanagementsystem.base.service;

import com.leverx.lms.learningmanagementsystem.base.config.FeatureFlagConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@Service
public class FeatureFlagService {

    private final WebClient webClient;

    public FeatureFlagService(FeatureFlagConfig config) {
        webClient = WebClient.builder()
                .baseUrl(config.getUri())
                .defaultHeader("Authorization", "Basic " + encodeBase64(config.getUsername(), config.getPassword()))
                .build();
    }

    public boolean isEnabled(String feature) {
        return webClient.get()
                .uri("/{feature}", feature)
                .exchangeToMono(response -> {
                    int status = response.statusCode().value();
                    switch (status) {
                        case 200 -> log.info("feature {} is active", feature);
                        case 204 -> log.warn("feature {} is disabled", feature);
                        case 404 -> log.error("feature {} not found", feature);
                        case 400 -> log.error("Flag input error for {}", feature);
                        default -> log.warn("Flag {} is inactive with status {}", feature, status);
                    }
                    return Mono.just(status == 200);
                })
                .blockOptional()
                .orElse(false);
    }

    private String encodeBase64(String username, String password) {
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}
