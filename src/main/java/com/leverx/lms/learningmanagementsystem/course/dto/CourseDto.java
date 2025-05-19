package com.leverx.lms.learningmanagementsystem.course.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CourseDto(
        UUID id,
        String title,
        String description,
        BigDecimal price,
        BigDecimal coinsPaid,
        CourseSettingsDto settings,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        List<LessonDto> lessons,
        @JsonBackReference
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        List<StudentDto> students
) {
}
