package com.leverx.lms.learningmanagementsystem.base.service;

import com.leverx.lms.learningmanagementsystem.base.config.UserProvidedMailServiceConfig;
import com.leverx.lms.learningmanagementsystem.base.dto.MailConfig;
import org.springframework.stereotype.Service;

@Service
public class UserProvidedMailServiceProcessor implements MailProcessor {

    private final UserProvidedMailServiceConfig mailConfig;

    public UserProvidedMailServiceProcessor(UserProvidedMailServiceConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    @Override
    public MailConfig getMailConfig() {
        return MailConfig.builder()
                .host(mailConfig.getHost())
                .username(mailConfig.getUsername())
                .password(mailConfig.getPassword())
                .build();
    }
}
