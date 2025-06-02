package com.leverx.lms.learningmanagementsystem.course.service;

import com.leverx.lms.learningmanagementsystem.base.enums.ProcessorType;
import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.base.service.FeatureFlagService;
import com.leverx.lms.learningmanagementsystem.base.service.MailService;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.lms.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.lms.learningmanagementsystem.student.service.StudentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
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
    private final StudentService studentService;
    private final MailService mailService;
    private final FeatureFlagService featureFlagService;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, @Lazy StudentService studentService,
                         MailService mailService, FeatureFlagService featureFlagService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.studentService = studentService;
        this.mailService = mailService;
        this.featureFlagService = featureFlagService;
    }

    @Transactional
    public CourseDto create(CourseDto courseDto) {
        var course = courseMapper.toEntity(courseDto);
        var savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    public CourseDto getById(UUID id) {
        var course = getEntityById(id);
        return courseMapper.toDto(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDto> getAll() {
        if (featureFlagService.isEnabled("course-by-description")) {
            return courseMapper.toDtoList(courseRepository.findAll(Sort.by("description")));
        }
        return courseMapper.toDtoList(courseRepository.findAll());
    }

    @Transactional
    public CourseDto update(UUID id, CourseDto courseDto) {
        var course = getEntityById(id);
        courseMapper.updateEntity(courseDto, course);
        var updatedCourse = courseRepository.save(course);
        return courseMapper.toDto(updatedCourse);
    }

    @Transactional
    public void delete(UUID id) {
        var course = getEntityById(id);
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

    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        var course = getById(courseId);
        var student = studentService.getById(studentId);
        course.students().add(student);
        courseRepository.save(courseMapper.toEntity(course));
        mailService.sendMail(student.email(), "Enrollment Confirmation",
                "You have been successfully enrolled in the course: " + course.title());
    }

    @Transactional
    public void dropStudent(UUID courseId, UUID studentId) {
        var course = getById(courseId);
        var student = studentService.getById(studentId);
        course.students().remove(student);
        courseRepository.save(courseMapper.toEntity(course));
        mailService.sendMail(student.email(), "Enrollment Cancellation",
                "You have been removed from the course: " + course.title());
    }


    public Course getEntityById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new BaseException("Course not found", NOT_FOUND));
    }
}
