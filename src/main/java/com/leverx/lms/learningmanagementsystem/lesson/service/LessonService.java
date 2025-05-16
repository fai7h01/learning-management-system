package com.leverx.lms.learningmanagementsystem.lesson.service;

import com.leverx.lms.learningmanagementsystem.lesson.mapper.LessonMapper;
import com.leverx.lms.learningmanagementsystem.lesson.repository.LessonRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }
}
