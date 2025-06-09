package com.leverx.lms.learningmanagementsystem.base.service.mail;

import com.leverx.lms.learningmanagementsystem.base.config.MailServiceConfig;
import com.leverx.lms.learningmanagementsystem.base.dto.MailConfig;
import org.springframework.stereotype.Service;

@Service
public class UserProvidedMailServiceProcessor implements MailProcessor {

    private final MailServiceConfig mailConfig;

    public UserProvidedMailServiceProcessor(MailServiceConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    @Override
    public MailConfig getMailConfig() {
        return MailConfig.builder()
                .host(mailConfig.getHost())
                .port(mailConfig.getPort())
                .username(mailConfig.getUsername())
                .password(mailConfig.getPassword())
                .build();
    }
}
