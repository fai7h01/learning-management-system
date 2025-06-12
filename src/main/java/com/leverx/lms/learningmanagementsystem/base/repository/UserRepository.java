package com.leverx.lms.learningmanagementsystem.base.repository;

import com.leverx.lms.learningmanagementsystem.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
