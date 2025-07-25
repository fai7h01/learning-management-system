package com.leverx.lms.learningmanagementsystem.lesson.repository;

import com.leverx.lms.learningmanagementsystem.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

}
