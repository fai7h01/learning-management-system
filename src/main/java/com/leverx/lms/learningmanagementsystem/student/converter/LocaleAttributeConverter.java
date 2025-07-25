package com.leverx.lms.learningmanagementsystem.student.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter(autoApply = true)
public class LocaleAttributeConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return locale == null ? null : locale.toLanguageTag();
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Locale.forLanguageTag(dbData);
    }
}
