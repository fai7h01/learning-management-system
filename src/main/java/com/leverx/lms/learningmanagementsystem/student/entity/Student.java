package com.leverx.lms.learningmanagementsystem.student.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.User;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "students")
@SQLRestriction("is_deleted = false")
public class Student extends User {

    private LocalDate dateOfBirth;
    private BigDecimal coins;
    @ManyToMany
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

}
