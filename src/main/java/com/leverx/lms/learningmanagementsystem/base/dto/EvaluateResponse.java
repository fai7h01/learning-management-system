package com.leverx.lms.learningmanagementsystem.base.dto;

import java.util.Map;

public record EvaluateResponse(Map<String, FlagResult> flags) {
}