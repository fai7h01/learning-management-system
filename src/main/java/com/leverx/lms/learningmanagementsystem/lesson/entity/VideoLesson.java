package com.leverx.lms.learningmanagementsystem.lesson.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@DiscriminatorValue(value = "VIDEO")
public class VideoLesson extends Lesson {

    private String url;
    private String platform;
}
