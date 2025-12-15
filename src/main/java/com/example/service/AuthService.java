package com.example.service;

import com.example.entity.User;

public interface AuthService {
    boolean register(String email, String rawPassword);
    User login(String email, String rawPassword);
}
