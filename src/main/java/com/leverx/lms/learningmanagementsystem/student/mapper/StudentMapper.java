package com.leverx.lms.learningmanagementsystem.student.mapper;

import com.leverx.lms.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;
import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface StudentMapper {

    StudentDto toDto(Student student);

    Student toEntity(StudentDto studentDto);
}
