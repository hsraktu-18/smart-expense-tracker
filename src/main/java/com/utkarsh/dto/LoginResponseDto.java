package com.utkarsh.dto;

public class LoginResponseDto {

    private String message;
    private Long userId;

    public LoginResponseDto(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public Long getUserId() {
        return userId;
    }
}
