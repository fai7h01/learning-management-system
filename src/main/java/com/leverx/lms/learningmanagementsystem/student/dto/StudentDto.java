package com.leverx.lms.learningmanagementsystem.student.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StudentDto(
        @JsonProperty(access = READ_ONLY)
        UUID id,
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must be less than 100 characters")
        String firstName,
        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must be less than 100 characters")
        String lastName,
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,
        @PositiveOrZero(message = "Coins cannot be negative")
        BigDecimal coins,
        @JsonManagedReference
        List<CourseDto> courses,
        @NotNull
        Locale locale
) {
}
