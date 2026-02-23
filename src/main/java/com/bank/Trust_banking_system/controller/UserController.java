package com.bank.Trust_banking_system.controller;



import com.bank.Trust_banking_system.dto.LoginRequest;
import com.bank.Trust_banking_system.dto.RegisterRequest;
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

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
