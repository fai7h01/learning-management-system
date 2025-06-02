package com.leverx.lms.learningmanagementsystem.course.mapper;

import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.lesson.mapper.LessonMapper;
import com.leverx.lms.learningmanagementsystem.student.mapper.StudentMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseSettingsMapper.class, LessonMapper.class})
public interface CourseMapper {

    CourseDto toDto(Course course);

    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "students", ignore = true)
    Course toEntity(CourseDto courseDto);

    List<CourseDto> toDtoList(List<Course> courses);

    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "students", ignore = true)
    void updateEntity(CourseDto courseDto, @MappingTarget Course course);
}
