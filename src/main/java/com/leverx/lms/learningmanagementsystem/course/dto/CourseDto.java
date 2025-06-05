package com.leverx.lms.learningmanagementsystem.course.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CourseDto(
        @JsonProperty(access = READ_ONLY)
        UUID id,
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be less than 255 characters")
        String title,
        @Size(max = 1000, message = "Description must be less than 1000 characters")
        String description,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,
        BigDecimal coinsPaid,
        @NotNull(message = "Course settings are required")
        CourseSettingsDto settings,
        @JsonProperty(access = READ_ONLY)
        List<LessonDto> lessons,
        @JsonBackReference
        @JsonProperty(access = READ_ONLY)
        List<StudentDto> students
) {
}
