package com.leverx.lms.learningmanagementsystem.course.repository;

import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findAllBySettings_StartDate(LocalDateTime startDate);
}
