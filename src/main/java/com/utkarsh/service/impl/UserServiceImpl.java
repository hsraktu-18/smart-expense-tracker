package com.utkarsh.service.impl;

import com.utkarsh.dto.LoginRequestDto;
import com.utkarsh.dto.LoginResponseDto;
import com.utkarsh.dto.UserResponseDto;
import com.utkarsh.entity.User;
import com.utkarsh.exception.EmailAlreadyExistsException;
import com.utkarsh.repository.UserRepository;
import com.utkarsh.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDto registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean passwordMatched = passwordEncoder
                .matches(loginRequestDto.getPassword(), user.getPassword());

        if (!passwordMatched){
            throw new RuntimeException("Invalid email or password");
        }
        return new LoginResponseDto("Login successful", user.getId());
    }
}
