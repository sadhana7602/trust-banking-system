package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.dto.LoginResponse;
import com.bank.Trust_banking_system.security.JwtUtil;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final JwtUtil jwtUtil;

    public AdminService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(String username, String password) {

        if (!"admin".equals(username) || !"admin".equals(password)) {
            throw new RuntimeException("Invalid admin credentials");
        }

        String token = jwtUtil.generateToken("admin");

        return new LoginResponse(
                token,
                "admin@trustbank.com",
                "Administrator",
                0L
        );
    }
}