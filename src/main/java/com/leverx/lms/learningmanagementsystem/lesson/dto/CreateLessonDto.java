package com.leverx.lms.learningmanagementsystem.lesson.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateLessonDto {
    private UUID courseId;
    private String title;
    private Integer duration;

}
