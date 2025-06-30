package com.leverx.lms.learningmanagementsystem.lesson.repository;

import com.leverx.lms.learningmanagementsystem.lesson.entity.VideoLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoLessonRepository extends JpaRepository<VideoLesson, UUID> {
}
