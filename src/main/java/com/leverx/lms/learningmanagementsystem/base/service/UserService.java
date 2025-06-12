package com.leverx.lms.learningmanagementsystem.base.service;

import com.leverx.lms.learningmanagementsystem.base.dto.UserDto;
import com.leverx.lms.learningmanagementsystem.base.mapper.UserMapper;
import com.leverx.lms.learningmanagementsystem.base.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
