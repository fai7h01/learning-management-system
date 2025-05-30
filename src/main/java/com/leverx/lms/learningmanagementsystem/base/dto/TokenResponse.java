package com.leverx.lms.learningmanagementsystem.base.dto;

public record TokenResponse(String access_token, long expires_in) {}