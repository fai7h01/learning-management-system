package com.leverx.lms.learningmanagementsystem.base.service;

import com.leverx.lms.learningmanagementsystem.base.dto.MailConfig;
import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DestinationMailServiceProcessor implements MailProcessor {

    private static final String DESTINATION_NAME = "mailtrap";
    private static final String MAIL_HOST = "mail.smtp.host";
    private static final String MAIL_PORT = "mail.smtp.port";
    private static final String MAIL_USER = "mail.user";
    private static final String MAIL_PASSWORD = "mail.user";

    @Override
    public MailConfig getMailConfig() {
        var destination = DestinationAccessor.getLoader()
                .tryGetDestination(DESTINATION_NAME)
                .getOrElseThrow(() -> new BaseException("Failed to load destination", HttpStatus.BAD_REQUEST));
        var host = (String) destination.get(MAIL_HOST).getOrElseThrow(() -> new BaseException("SMTP host not found in destination", HttpStatus.BAD_REQUEST));
        var port = (String) destination.get(MAIL_PORT).getOrElseThrow(() -> new BaseException("SMTP port not found in destination", HttpStatus.BAD_REQUEST));
        var user = (String) destination.get(MAIL_USER).getOrElseThrow(() -> new BaseException("SMTP user not found in destination", HttpStatus.BAD_REQUEST));
        var password = (String) destination.get(MAIL_PASSWORD).getOrElseThrow(() -> new BaseException("SMTP user not found in destination", HttpStatus.BAD_REQUEST));

        return MailConfig.builder()
                .host(host)
                .port(port)
                .username(user)
                .password(password)
                .build();
    }
}
