package com.leverx.lms.learningmanagementsystem.course.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class CourseSchedule {

    private final CourseService courseService;

    public CourseSchedule(CourseService courseService) {
        this.courseService = courseService;
    }

    @Scheduled(fixedRate = 60 * 100000) // every 60 minutes
    public void collectCourses() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var courses = courseService.findAllByStartDate(tomorrow);
    }
}
