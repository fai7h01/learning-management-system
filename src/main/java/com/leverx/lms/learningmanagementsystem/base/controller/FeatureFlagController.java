package com.leverx.lms.learningmanagementsystem.base.controller;

import com.leverx.lms.learningmanagementsystem.base.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feature-flags")
public class FeatureFlagController extends BaseController{

    private final FeatureFlagService featureFlagService;

    public FeatureFlagController(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @GetMapping("/{feature}")
    public ResponseEntity<?> isFeatureEnabled(@PathVariable("feature") String feature) {
        return buildSuccessResponse(featureFlagService.isEnabled(feature));
    }
}
