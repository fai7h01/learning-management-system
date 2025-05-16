package com.leverx.lms.learningmanagementsystem.student.repository;

import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

}
