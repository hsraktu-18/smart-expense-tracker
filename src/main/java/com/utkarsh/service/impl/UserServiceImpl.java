package com.utkarsh.service.impl;

import com.utkarsh.entity.User;
import com.utkarsh.repository.UserRepository;
import com.utkarsh.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
