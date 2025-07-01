package com.leverx.lms.learningmanagementsystem.lesson.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.lesson.enums.LessonType;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LessonDto(
        UUID id,
        String title,
        Integer duration,
        CourseDto course,
        LessonType type,
        String url,
        String platform,
        String location,
        Integer capacity
) {
}