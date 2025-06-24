package com.leverx.lms.learningmanagementsystem.course.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
import com.leverx.lms.learningmanagementsystem.lesson.entity.Lesson;
import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
@SQLRestriction("is_deleted = false")
public class Course extends BaseEntity {

    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal coinsPaid;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "settings_id", referencedColumnName = "id")
    private CourseSettings settings;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lesson> lessons = new HashSet<>();
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
