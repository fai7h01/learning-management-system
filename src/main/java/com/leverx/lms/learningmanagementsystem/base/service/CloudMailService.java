package com.leverx.lms.learningmanagementsystem.base.service;

import com.leverx.lms.learningmanagementsystem.base.enums.ProcessorType;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.leverx.lms.learningmanagementsystem.base.enums.ProcessorType.DESTINATION_SERVICE;
import static com.leverx.lms.learningmanagementsystem.base.enums.ProcessorType.USER_PROVIDED_SERVICE;

@Profile("cloud")
@Service
public class CloudMailService implements MailService {

    private final MailFactory mailFactory;
    private final FeatureFlagService featureFlagService;

    public CloudMailService(MailFactory mailFactory, FeatureFlagService featureFlagService) {
        this.mailFactory = mailFactory;
        this.featureFlagService = featureFlagService;
    }

    @Async
    public void sendMail(String to, String subject, String text) {
        var processorType = getProcessorType();
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

    public ProcessorType getProcessorType() {
        if (featureFlagService.isEnabled(DESTINATION_SERVICE.getValue())) {
            return DESTINATION_SERVICE;
        } else {
            return USER_PROVIDED_SERVICE;
        }
    }
}
