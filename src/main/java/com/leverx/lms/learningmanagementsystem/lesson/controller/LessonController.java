package com.leverx.lms.learningmanagementsystem.lesson.controller;

import com.leverx.lms.learningmanagementsystem.base.controller.BaseController;
import com.leverx.lms.learningmanagementsystem.lesson.dto.ClassroomLessonCreateDto;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.dto.VideoLessonCreateDto;
import com.leverx.lms.learningmanagementsystem.lesson.enums.LessonType;
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
    public ResponseEntity<?> getAllLessons(@RequestParam(value = "type", required = false) LessonType lessonType,
                                           @PageableDefault(page = 0, size = 10) Pageable pageable) {

        if (lessonType == null) {
            return buildSuccessResponse(lessonService.getAll(pageable));
        }
        return switch (lessonType) {
            case VIDEO -> buildSuccessResponse(lessonService.getAllVideoLessons(pageable));
            case CLASSROOM -> buildSuccessResponse(lessonService.getAllClassroomLessons(pageable));
        };
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable UUID id) {
        return buildSuccessResponse(lessonService.getById(id));
    }

    @PostMapping("/classroom")
    public ResponseEntity<?> createClassLesson(@RequestBody ClassroomLessonCreateDto lessonDto) {
        return buildCreatedResponse(lessonService.createClassLesson(lessonDto));
    }

    @PostMapping("/video")
    public ResponseEntity<?> createVideoLesson(@RequestBody VideoLessonCreateDto lessonDto) {
        return buildCreatedResponse(lessonService.createVideoLesson(lessonDto));
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
