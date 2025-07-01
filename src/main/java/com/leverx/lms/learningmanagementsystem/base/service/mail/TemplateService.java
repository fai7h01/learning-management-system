package com.leverx.lms.learningmanagementsystem.base.service.mail;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

@Service
public class TemplateService {

    public String renderTemplate(String templateName, Map<String, Object> data) {
        MustacheFactory mf = new DefaultMustacheFactory("templates/mail");
        Mustache mustache = mf.compile(templateName);
        StringWriter writer = new StringWriter();
        mustache.execute(writer, data);
        return writer.toString();
    }
}
