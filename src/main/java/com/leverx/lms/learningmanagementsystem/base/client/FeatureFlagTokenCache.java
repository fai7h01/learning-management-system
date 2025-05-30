package com.leverx.lms.learningmanagementsystem.base.client;

import com.leverx.lms.learningmanagementsystem.base.config.FeatureFlagConfig;
import com.leverx.lms.learningmanagementsystem.base.dto.TokenResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Configuration
public class FeatureFlagTokenCache {

    private final FeatureFlagConfig config;
    private final WebClient http;

    private volatile String token;
    private volatile Instant expiry = Instant.EPOCH;

    public FeatureFlagTokenCache(FeatureFlagConfig config) {
        this.config = config;
        http = WebClient.builder().build();
    }

    public String get() {
        if (Instant.now().isBefore(expiry.minusSeconds(60))) {
            return token;
        }

        // fetch new token
        var responseFuture = http.post()
                .uri(config.getUri() + "/oauth/token")
                .headers(h -> h.setBasicAuth(config.getUsername(), config.getPassword()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .toFuture();

        try{
           var response = responseFuture.get();// wait for the future to complete
            if (response == null || response.access_token() == null) {
                throw new RuntimeException("Failed to fetch token or token is null");
            }
            token = response.access_token();
            expiry = Instant.now().plusSeconds(response.expires_in());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch token from Feature Flag service", e);
        }
        return token;
    }
}
