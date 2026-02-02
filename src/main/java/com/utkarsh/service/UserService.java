package com.utkarsh.service;

import com.utkarsh.dto.LoginRequestDto;
import com.utkarsh.dto.LoginResponseDto;
import com.utkarsh.dto.UserResponseDto;
import com.utkarsh.entity.User;

public interface UserService {
    UserResponseDto registerUser(User user );
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
