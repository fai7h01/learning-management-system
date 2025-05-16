package com.leverx.lms.learningmanagementsystem.student.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
public class Student extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private BigDecimal coins;
    @ManyToMany
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}
