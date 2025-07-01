package com.leverx.lms.learningmanagementsystem.lesson.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@SQLRestriction("is_deleted = false")
@DiscriminatorValue(value = "CLASSROOM")
public class ClassroomLesson extends Lesson {

    private String location;
    private Integer capacity;
}
