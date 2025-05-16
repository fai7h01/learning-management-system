package com.leverx.lms.learningmanagementsystem.course.controller;

import com.leverx.lms.learningmanagementsystem.base.controller.BaseController;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.service.CourseService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController extends BaseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return buildPaginatedResponse(courseService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable UUID id) {
        return buildSuccessResponse(courseService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto) {
        return buildCreatedResponse(courseService.create(courseDto));
    }
}
