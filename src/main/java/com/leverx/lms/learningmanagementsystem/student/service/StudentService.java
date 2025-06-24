package com.leverx.lms.learningmanagementsystem.student.service;

import com.leverx.lms.learningmanagementsystem.base.aspect.Lock;
import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.base.service.lock.LockService;
import com.leverx.lms.learningmanagementsystem.base.service.mail.MailService;
import com.leverx.lms.learningmanagementsystem.base.service.mail.NotificationService;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.course.service.CourseService;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;
import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import com.leverx.lms.learningmanagementsystem.student.mapper.StudentMapper;
import com.leverx.lms.learningmanagementsystem.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseService courseService;
    private final LockService lockService;
    private final NotificationService notificationService;

    @Transactional
    public StudentDto create(StudentDto studentDto) {
        var student = studentMapper.toEntity(studentDto);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentDto getById(UUID id) {
        return studentMapper.toDto(getEntityById(id));
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(studentMapper::toDto);
    }

    @Transactional
    public StudentDto update(UUID id, StudentDto studentDto) {
        var student = getEntityById(id);
        studentMapper.updateEntity(studentDto, student);
        var updatedStudent = studentRepository.save(student);
        return studentMapper.toDto(updatedStudent);
    }

    @Transactional
    public void delete(UUID id) {
        var student = getEntityById(id);
        student.setDeleted(true);
        studentRepository.save(student);
    }

    public Student getEntityById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new BaseException("Student not found", NOT_FOUND));
    }

    @Lock(key = "'buy-course:' + #studentId")
    @Transactional
    public void buyCourseWithLockAnnotation(UUID studentId, UUID courseId) {
        var student = getEntityById(studentId);
        var course = courseService.getEntityById(courseId);
        validateBuyCourse(student, course);
        student.setCoins(student.getCoins().subtract(course.getPrice()));
        student.getCourses().add(course);
        studentRepository.save(student);
        studentRepository.flush();
    }

    private void validateBuyCourse(Student student, Course course) {
        if (student.getCourses().contains(course)) {
            throw new BaseException("Student already enrolled in this course", HttpStatus.BAD_REQUEST);
        }
        if (student.getCoins().compareTo(course.getPrice()) < 0) {
            throw new BaseException("Insufficient coins to buy the course", HttpStatus.BAD_REQUEST);
        }
    }
}
