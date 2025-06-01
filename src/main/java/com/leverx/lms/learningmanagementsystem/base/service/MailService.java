package com.leverx.lms.learningmanagementsystem.base.service;

import com.leverx.lms.learningmanagementsystem.base.enums.ProcessorType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailFactory mailFactory;

    public MailService(MailFactory mailFactory) {
        this.mailFactory = mailFactory;
    }

    public void sendMail(String to, String subject, String text, ProcessorType processorType) {

        var processor = mailFactory.getProcessor(processorType);
        var mailConfig = processor.getMailConfig();
        var configuredMailSender = MailSenderConfig.getJavaMailSender(mailConfig);

        var message = new SimpleMailMessage();
        message.setFrom(mailConfig.username());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        configuredMailSender.send(message);
    }
}
