package com.leverx.lms.learningmanagementsystem.base.service.mail;

import com.leverx.lms.learningmanagementsystem.base.dto.MailConfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSenderUtil {

    private MailSenderUtil() {}

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";

    public static JavaMailSender getJavaMailSender(MailConfig smtp) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtp.host());
        mailSender.setPort(Integer.parseInt(smtp.port()));
        mailSender.setUsername(smtp.username());
        mailSender.setPassword(smtp.password());
        var props = mailSender.getJavaMailProperties();
        props.put(MAIL_SMTP_AUTH, "true");
        props.put(MAIL_SMTP_STARTTLS, "true");
        return mailSender;
    }
}
