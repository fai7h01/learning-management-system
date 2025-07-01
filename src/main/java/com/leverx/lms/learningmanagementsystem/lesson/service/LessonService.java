package com.leverx.lms.learningmanagementsystem.lesson.service;

import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.service.CourseService;
import com.leverx.lms.learningmanagementsystem.lesson.dto.*;
import com.leverx.lms.learningmanagementsystem.lesson.entity.Lesson;
import com.leverx.lms.learningmanagementsystem.lesson.mapper.LessonMapper;
import com.leverx.lms.learningmanagementsystem.lesson.repository.ClassroomLessonRepository;
import com.leverx.lms.learningmanagementsystem.lesson.repository.LessonRepository;
import com.leverx.lms.learningmanagementsystem.lesson.repository.VideoLessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final VideoLessonRepository videoLessonRepository;
    private final ClassroomLessonRepository classroomLessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseService courseService;

    @Transactional
    public LessonDto createClassLesson(ClassroomLessonCreateDto request) {
        return createLesson(request.courseId(), request, lessonMapper::toEntity, lessonMapper::toDto);
    }

    @Transactional
    public LessonDto createVideoLesson(VideoLessonCreateDto request) {
        return createLesson(request.courseId(), request, lessonMapper::toEntity, lessonMapper::toDto);
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

    @Transactional(readOnly = true)
    public Page<LessonDto> getAllVideoLessons(Pageable pageable) {
        return videoLessonRepository.findAll(pageable)
                .map(lessonMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LessonDto> getAllClassroomLessons(Pageable pageable) {
        return classroomLessonRepository.findAll(pageable)
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

    private <Req, Entity extends Lesson> LessonDto createLesson(
            UUID courseId,
            Req request,
            BiFunction<Req, CourseDto, Entity> toEntity,
            Function<Entity, LessonDto> toDto
    ) {
        CourseDto course = courseService.getById(courseId);
        Entity lesson = toEntity.apply(request, course);
        Entity saved = lessonRepository.save(lesson);
        return toDto.apply(saved);
    }
}
