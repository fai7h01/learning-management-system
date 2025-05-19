package com.leverx.lms.learningmanagementsystem.lesson.mapper;

import com.leverx.lms.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface LessonMapper {

    @Mapping(target = "course", ignore = true)
    LessonDto toDto(Lesson lesson);

    @Mapping(target = "course", ignore = true)
    Lesson toEntity(LessonDto lessonDto);

    void updateEntity(LessonDto lessonDto, @MappingTarget Lesson lesson);
}
