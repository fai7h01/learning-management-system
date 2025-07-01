package com.leverx.lms.learningmanagementsystem.lesson.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ClassroomLessonCreateDto(
        String title,
        Integer duration,
        UUID courseId,
        String location,
        Integer capacity
) {}
