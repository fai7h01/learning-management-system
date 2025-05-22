package com.leverx.lms.learningmanagementsystem.student.controller;

import com.leverx.lms.learningmanagementsystem.base.controller.BaseController;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;
import com.leverx.lms.learningmanagementsystem.student.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController extends BaseController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents(@PageableDefault(page = 0, size = 10)
                                                Pageable pageable) {
        return buildPaginatedResponse(studentService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable UUID id) {
        return buildSuccessResponse(studentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentDto studentDto) {
        return buildCreatedResponse(studentService.create(studentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable UUID id, @RequestBody StudentDto studentDto) {
        return buildSuccessResponse(studentService.update(id, studentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.delete(id);
        return noContent().build();
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> buyCourse(@PathVariable UUID studentId, @PathVariable UUID courseId) {
        studentService.buyCourse(studentId, courseId);
        return noContent().build();
    }
}
