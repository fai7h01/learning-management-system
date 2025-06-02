package com.leverx.lms.learningmanagementsystem.course.controller;

import com.leverx.lms.learningmanagementsystem.base.controller.BaseController;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.service.CourseService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController extends BaseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return buildSuccessResponse(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable UUID id) {
        return buildSuccessResponse(courseService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto) {
        return buildCreatedResponse(courseService.create(courseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable("id") UUID id,
                                          @RequestBody CourseDto courseDto) {
        return buildSuccessResponse(courseService.update(id, courseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") UUID id) {
        courseService.delete(id);
        return noContent().build();
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Void> enrollStudent(@PathVariable UUID courseId,
                                              @PathVariable UUID studentId) {
        courseService.enrollStudent(courseId, studentId);
        return noContent().build();
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Void> dropStudent(@PathVariable UUID courseId,
                                            @PathVariable UUID studentId) {
        courseService.dropStudent(courseId, studentId);
        return noContent().build();
    }
}
