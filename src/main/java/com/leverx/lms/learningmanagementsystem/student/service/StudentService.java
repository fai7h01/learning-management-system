package com.leverx.lms.learningmanagementsystem.student.service;

import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;
import com.leverx.lms.learningmanagementsystem.student.mapper.StudentMapper;
import com.leverx.lms.learningmanagementsystem.student.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional
    public StudentDto create(StudentDto studentDto) {
        var student = studentMapper.toEntity(studentDto);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentDto getById(UUID id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new BaseException("Student not found", NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(studentMapper::toDto);
    }

    @Transactional
    public StudentDto update(UUID id, StudentDto studentDto) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new BaseException("Student not found", NOT_FOUND));
        studentMapper.updateEntity(studentDto, student);
        var updatedStudent = studentRepository.save(student);
        return studentMapper.toDto(updatedStudent);
    }

    @Transactional
    public void delete(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new BaseException("Student not found", NOT_FOUND));
        student.setDeleted(true);
        studentRepository.save(student);
    }

    public void enroll() {

    }
}
