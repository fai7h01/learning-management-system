package com.leverx.lms.learningmanagementsystem.course.mapper;

import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.lesson.mapper.LessonMapper;
import com.leverx.lms.learningmanagementsystem.student.mapper.StudentMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseSettingsMapper.class, LessonMapper.class})
public interface CourseMapper {

    CourseDto toDto(Course course);

    Course toEntity(CourseDto courseDto);
}
