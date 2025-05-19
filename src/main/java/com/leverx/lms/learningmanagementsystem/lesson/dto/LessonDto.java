package com.leverx.lms.learningmanagementsystem.lesson.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LessonDto(
        String title,
        Integer duration,
        CourseDto course
) {
}
