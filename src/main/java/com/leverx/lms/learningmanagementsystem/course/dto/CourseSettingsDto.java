package com.leverx.lms.learningmanagementsystem.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CourseSettingsDto(
        UUID id,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isPublic
) {
}
