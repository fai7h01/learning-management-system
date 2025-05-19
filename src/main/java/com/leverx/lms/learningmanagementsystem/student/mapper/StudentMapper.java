package com.leverx.lms.learningmanagementsystem.student.mapper;

import com.leverx.lms.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;
import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface StudentMapper {

    @Mapping(target = "courses", ignore = true)
    StudentDto toDto(Student student);

    @Mapping(target = "courses", ignore = true)
    Student toEntity(StudentDto studentDto);

    void updateEntity(StudentDto studentDto, @MappingTarget Student student);
}
