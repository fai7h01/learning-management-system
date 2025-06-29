package com.leverx.lms.learningmanagementsystem.course.entity;

import java.math.BigDecimal;
import java.util.UUID;

public interface CourseDetailView {

    UUID getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
}
