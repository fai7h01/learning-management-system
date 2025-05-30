package com.leverx.lms.learningmanagementsystem.base.client;

import com.leverx.lms.learningmanagementsystem.base.config.FeatureFlagConfig;
import com.leverx.lms.learningmanagementsystem.base.dto.EvaluateRequest;
import com.leverx.lms.learningmanagementsystem.base.dto.EvaluateResponse;
import com.leverx.lms.learningmanagementsystem.base.dto.FlagResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FeatureFlagClient {

    private final FeatureFlagTokenCache tokens;
    private final FeatureFlagConfig config;
    private final WebClient webClient;

    public FeatureFlagClient(FeatureFlagTokenCache tokens, FeatureFlagConfig config) {
        this.tokens = tokens;
        this.config = config;
        this.webClient = WebClient.builder().build();
    }

    private boolean isEnabled(String flag, Map<String, String> params) {
        var request = EvaluateRequest.builder()
                .flagNames(List.of(flag))
                .entityContext(params)
                .build();

        var response = webClient.post()
                .uri(config.getUri() + "/feature-flags/v1/evaluate")
                .headers(h -> h.setBearerAuth(tokens.get()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(EvaluateResponse.class)
                .block();

        return Optional.ofNullable(response)
                .map(EvaluateResponse::flags)
                .map(flags -> flags.get(flag))
                .map(FlagResult::enabled)
                .orElse(false);
    }
}
