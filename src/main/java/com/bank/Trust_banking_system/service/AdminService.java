package com.bank.Trust_banking_system.service;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) &&
                ADMIN_PASSWORD.equals(password);
    }
}