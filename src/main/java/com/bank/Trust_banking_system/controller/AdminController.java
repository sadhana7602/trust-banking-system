package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.AdminLoginRequest;
import com.bank.Trust_banking_system.dto.LoginResponse;
import com.bank.Trust_banking_system.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody AdminLoginRequest request) {
        return adminService.login(request.getUsername(), request.getPassword());
    }
}