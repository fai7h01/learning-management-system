package com.leverx.lms.learningmanagementsystem.course.service;

import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.lms.learningmanagementsystem.course.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Transactional
    public CourseDto create(CourseDto courseDto) {
        var course = courseMapper.toEntity(courseDto);
        var savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Transactional(readOnly = true)
    public CourseDto getById(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new BaseException("Course not found", NOT_FOUND));
        return courseMapper.toDto(course);
    }

    @Transactional(readOnly = true)
    public Page<CourseDto> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toDto);
    }

    @Transactional
    public CourseDto update(UUID id, CourseDto courseDto) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new BaseException("Course not found", NOT_FOUND));
        courseMapper.updateEntity(courseDto, course);
        var updatedCourse = courseRepository.save(course);
        return courseMapper.toDto(updatedCourse);
    }

    @Transactional
    public void delete(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new BaseException("Course not found", NOT_FOUND));
        course.setDeleted(true);
        courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDto> findAllByStartDate(LocalDateTime startDate) {
        return courseRepository.findAllBySettings_StartDate(startDate)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }
}
