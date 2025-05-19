package com.leverx.lms.learningmanagementsystem.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CourseSettingsDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isPublic
) {
}
