package com.leverx.lms.learningmanagementsystem.lesson.mapper;

import com.leverx.lms.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.entity.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface LessonMapper {

    LessonDto toDto(Lesson lesson);

    Lesson toEntity(LessonDto lessonDto);
}
