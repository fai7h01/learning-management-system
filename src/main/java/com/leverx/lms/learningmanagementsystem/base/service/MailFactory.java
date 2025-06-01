package com.leverx.lms.learningmanagementsystem.base.service;

import com.leverx.lms.learningmanagementsystem.base.enums.ProcessorType;
import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MailFactory {

    private final UserProvidedMailServiceProcessor userProvidedMailServiceProcessor;
    private final DestinationMailServiceProcessor destinationMailServiceProcessor;

    public MailFactory(UserProvidedMailServiceProcessor userProvidedMailServiceProcessor,
                       DestinationMailServiceProcessor destinationMailServiceProcessor) {
        this.userProvidedMailServiceProcessor = userProvidedMailServiceProcessor;
        this.destinationMailServiceProcessor = destinationMailServiceProcessor;
    }

    public MailProcessor getProcessor(ProcessorType type) {
        return switch (type) {
            case USER_PROVIDED_SERVICE -> userProvidedMailServiceProcessor;
            case DESTINATION_SERVICE -> destinationMailServiceProcessor;
            default -> throw new BaseException("Unknown mail processor type", HttpStatus.BAD_REQUEST);
        };
    }
}
