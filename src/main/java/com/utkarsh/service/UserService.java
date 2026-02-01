package com.utkarsh.service;

import com.utkarsh.dto.UserResponseDto;
import com.utkarsh.entity.User;

public interface UserService {
    UserResponseDto registerUser(User user );
}
