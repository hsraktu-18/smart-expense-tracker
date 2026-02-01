package com.utkarsh.controller;

import com.utkarsh.dto.UserResponseDto;
import com.utkarsh.entity.User;
import com.utkarsh.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody User user){

        UserResponseDto response = userService.registerUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
