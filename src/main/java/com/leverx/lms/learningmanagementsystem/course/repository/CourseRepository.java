package com.leverx.lms.learningmanagementsystem.course.repository;

import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.course.entity.CourseDetailView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query("SELECT c FROM Course c")
    Page<CourseDetailView> findCourseDetails(Pageable pageable);

    @EntityGraph(attributePaths = "{settings, lessons}")
    List<Course> findAllBySettings_StartDate(LocalDateTime startDate);

    @EntityGraph(attributePaths = {"settings", "lessons"})
    List<Course> findAllByStudents_Id(UUID studentId);
}
