package com.leverx.lms.learningmanagementsystem.base.service.mail;

public interface MailService {

    void sendMail(String to, String subject, String text);
}
