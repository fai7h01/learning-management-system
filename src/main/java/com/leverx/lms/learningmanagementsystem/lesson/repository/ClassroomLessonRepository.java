package com.leverx.lms.learningmanagementsystem.lesson.repository;

import com.leverx.lms.learningmanagementsystem.lesson.entity.ClassroomLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassroomLessonRepository extends JpaRepository<ClassroomLesson, UUID> {
}
