package com.leverx.lms.learningmanagementsystem.base.service.mail;

import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MailService mailService;
    private final TemplateService templateService;

    public void sendCourseStartNotification(Student student, Course course) {
        var locale = student.getLocale().getLanguage() != null ? student.getLocale().getLanguage() : "en";
        var templateFile = String.format("course_start_%s.mustache", locale);
        Map<String, Object> model = Map.of(
                "name", student.getFirstName(),
                "courseName", course.getTitle(),
                "startDate", course.getSettings().getStartDate().toString()
        );
        var body = templateService.renderTemplate(templateFile, model);
        var subject = getLocalizedSubject(locale);
        mailService.sendMail(student.getEmail(), subject, body);
    }

    private String getLocalizedSubject(String locale) {
        return switch (locale) {
            case "ru" -> "Курс начинается!";
            case "ka" -> "კურსი იწყება!";
            default -> "Your Course is Starting!";
        };
    }
}
