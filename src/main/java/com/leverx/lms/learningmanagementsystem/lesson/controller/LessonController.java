package com.leverx.lms.learningmanagementsystem.lesson.controller;

import com.leverx.lms.learningmanagementsystem.base.controller.BaseController;
import com.leverx.lms.learningmanagementsystem.lesson.dto.CreateLessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.service.LessonService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonController extends BaseController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLessons(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return buildSuccessResponse(lessonService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable UUID id) {
        return buildSuccessResponse(lessonService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createLesson(@RequestBody CreateLessonDto lessonDto) {
        return buildCreatedResponse(lessonService.create(lessonDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable("id") UUID id,
                                          @RequestBody LessonDto lessonDto) {
        return buildSuccessResponse(lessonService.update(id, lessonDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable("id") UUID id) {
        lessonService.delete(id);
        return noContent().build();
    }
}
