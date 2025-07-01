package com.leverx.lms.learningmanagementsystem.lesson.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.lesson.enums.LessonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
@SQLRestriction("is_deleted = false")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Lesson extends BaseEntity {

    private String title;
    private Integer duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @Enumerated(EnumType.STRING)
    private LessonType type;
}
