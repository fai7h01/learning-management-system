package com.leverx.lms.learningmanagementsystem.base.dto;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record EvaluateRequest(List<String> flagNames,
                              Map<String, String> entityContext) {
}
