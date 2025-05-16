package com.leverx.lms.learningmanagementsystem.course.service;

import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.lms.learningmanagementsystem.course.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public CourseDto create(CourseDto courseDto) {
        var course = courseMapper.toEntity(courseDto);
        var savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    public CourseDto getById(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new BaseException("Course not found", NOT_FOUND));
        return courseMapper.toDto(course);
    }

    public Page<CourseDto> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toDto);
    }
}
