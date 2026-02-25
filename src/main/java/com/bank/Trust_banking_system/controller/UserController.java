package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.LoginResponse;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestParam String email,
                               @RequestParam String password) {
        return userService.login(email, password);
    }
}