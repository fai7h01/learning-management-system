package com.leverx.lms.learningmanagementsystem.course.mapper;

import com.leverx.lms.learningmanagementsystem.course.dto.CourseSettingsDto;
import com.leverx.lms.learningmanagementsystem.course.entity.CourseSettings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseSettingsMapper {

    CourseSettingsDto toDto(CourseSettings courseSettings);

    CourseSettings toEntity(CourseSettingsDto courseSettingsDto);
}
