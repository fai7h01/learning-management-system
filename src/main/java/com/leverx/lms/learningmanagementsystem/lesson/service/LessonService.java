package com.leverx.lms.learningmanagementsystem.lesson.service;

import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.mapper.LessonMapper;
import com.leverx.lms.learningmanagementsystem.lesson.repository.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    public LessonDto create(LessonDto lessonDto) {
        var lesson = lessonMapper.toEntity(lessonDto);
        var savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(savedLesson);
    }

    public LessonDto getById(UUID id) {
        var lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BaseException("Lesson not found", NOT_FOUND));
        return lessonMapper.toDto(lesson);
    }

    public Page<LessonDto> getAll(Pageable pageable) {
        return lessonRepository.findAll(pageable)
                .map(lessonMapper::toDto);
    }

    public LessonDto update(UUID id, LessonDto lessonDto) {
        var lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BaseException("Lesson not found", NOT_FOUND));
        lessonMapper.updateEntity(lessonDto, lesson);
        var updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(updatedLesson);
    }

    public void delete(UUID id) {
        var lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BaseException("Lesson not found", NOT_FOUND));
        lesson.setDeleted(true);
        lessonRepository.save(lesson);
    }
}
