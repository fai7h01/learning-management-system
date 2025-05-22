package com.leverx.lms.learningmanagementsystem.lesson.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LessonDto(
        @JsonProperty(access = READ_ONLY)
        UUID id,
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be less than 255 characters")
        String title,
        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be positive")
        Integer duration,
        @NotNull(message = "Course is required")
        CourseDto course
) {
}
