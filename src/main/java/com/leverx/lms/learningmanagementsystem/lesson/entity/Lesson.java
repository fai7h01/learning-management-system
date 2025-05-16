package com.leverx.lms.learningmanagementsystem.lesson.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    private String title;
    private Integer duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
