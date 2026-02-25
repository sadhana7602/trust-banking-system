package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.dto.LoginResponse;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.exception.InvalidCredentialsException;
import com.bank.Trust_banking_system.repository.UserRepository;
import com.bank.Trust_banking_system.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
    }

    // 🔹 REGISTER USER
    public User register(User user) {

        // ✅ Check duplicate email BEFORE saving
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // ✅ Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // ✅ Send welcome email
        emailService.sendMail(
                savedUser.getEmail(),
                "Welcome to Trust Bank 🎉",
                "Hello " + savedUser.getFullName() +
                        ",\n\nYour registration is successful.\n\n" +
                        "Thank you for choosing Trust Bank."
        );

        return savedUser;
    }

    // 🔹 LOGIN WITH JWT
    public LoginResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getEmail(),
                user.getFullName(),
                user.getId()
        );
    }
}