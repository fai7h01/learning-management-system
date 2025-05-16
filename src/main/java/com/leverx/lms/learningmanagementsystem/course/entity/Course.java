package com.leverx.lms.learningmanagementsystem.course.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
import com.leverx.lms.learningmanagementsystem.lesson.entity.Lesson;
import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal coinsPaid;
    @OneToOne
    private CourseSettings settings;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lesson;
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Student> students;
}
