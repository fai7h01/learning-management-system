package com.leverx.lms.learningmanagementsystem.base.dto;

import lombok.Builder;

@Builder
public record MailConfig(
        String host,
        String port,
        String username,
        String password
) {
}
