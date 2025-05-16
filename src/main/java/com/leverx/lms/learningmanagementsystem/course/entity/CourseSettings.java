package com.leverx.lms.learningmanagementsystem.course.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_settings")
public class CourseSettings extends BaseEntity {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isPublic;
}
