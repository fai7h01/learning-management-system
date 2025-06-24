package com.leverx.lms.learningmanagementsystem.lesson.entity;

import com.leverx.lms.learningmanagementsystem.base.entity.BaseEntity;
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
@Table(name = "video_lessons")
@SQLRestriction("is_deleted = false")
public class VideoLesson extends BaseEntity {

    private String url;
    private String platform;
}
