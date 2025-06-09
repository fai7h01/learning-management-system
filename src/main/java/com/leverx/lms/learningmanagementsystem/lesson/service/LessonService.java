package com.leverx.lms.learningmanagementsystem.lesson.service;

import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.service.CourseService;
import com.leverx.lms.learningmanagementsystem.lesson.dto.CreateLessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.mapper.LessonMapper;
import com.leverx.lms.learningmanagementsystem.lesson.repository.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseService courseService;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper, CourseService courseService) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.courseService = courseService;
    }

    @Transactional
    public LessonDto create(CreateLessonDto request) {
        CourseDto course = courseService.getById(request.getCourseId());
        var lesson = lessonMapper.toEntity(request, course);
        var savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(savedLesson);
    }

    @Transactional(readOnly = true)
    public LessonDto getById(UUID id) {
        var lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BaseException("Lesson not found", NOT_FOUND));
        return lessonMapper.toDto(lesson);
    }

    @Transactional(readOnly = true)
    public Page<LessonDto> getAll(Pageable pageable) {
        return lessonRepository.findAll(pageable)
                .map(lessonMapper::toDto);
    }

    @Transactional
    public LessonDto update(UUID id, LessonDto lessonDto) {
        var lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BaseException("Lesson not found", NOT_FOUND));
        lessonMapper.updateEntity(lessonDto, lesson);
        var updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(updatedLesson);
    }

    @Transactional
    public void delete(UUID id) {
        var lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BaseException("Lesson not found", NOT_FOUND));
        lesson.setDeleted(true);
        lessonRepository.save(lesson);
    }
}
