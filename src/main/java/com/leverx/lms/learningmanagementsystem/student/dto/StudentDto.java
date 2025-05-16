package com.leverx.lms.learningmanagementsystem.student.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StudentDto(
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        BigDecimal coins,
        @JsonManagedReference
        List<CourseDto> courses
) {
}
