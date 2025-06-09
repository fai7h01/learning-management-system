package com.leverx.lms.learningmanagementsystem.base.service.ffs;

import com.leverx.lms.learningmanagementsystem.base.config.FeatureFlagConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Profile("cloud")
@Slf4j
@Service
public class FeatureFlagService {

    private final WebClient webClient;
    private final FeatureFlagConfig config;

    public FeatureFlagService(FeatureFlagConfig config) {
        this.config = config;
        webClient = WebClient.builder()
                .baseUrl(this.config.getUri() + "/api/v1/evaluate")
                .defaultHeader("Authorization", "Basic " + encodeBase64(this.config.getUsername(), this.config.getPassword()))
                .build();
    }

    public boolean isEnabled(String feature) {
        return webClient.get()
                .uri("/" + feature)
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
