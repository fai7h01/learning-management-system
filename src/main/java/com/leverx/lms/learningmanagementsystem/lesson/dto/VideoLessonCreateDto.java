package com.leverx.lms.learningmanagementsystem.lesson.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record VideoLessonCreateDto(
        String title,
        Integer duration,
        UUID courseId,
        String url,
        String platform
) {}
