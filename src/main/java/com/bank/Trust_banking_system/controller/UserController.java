package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 REGISTER USER
    @PostMapping("/register")
    public User register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    // 🔹 LOGIN USER
    @PostMapping("/login")
    public User login(@RequestParam String email,
                      @RequestParam String password) {
        System.out.println("I am in");
        return userService.login(email, password);
    }
}