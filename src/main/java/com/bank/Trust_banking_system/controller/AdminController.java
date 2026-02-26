package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.AdminLoginRequest;
import com.bank.Trust_banking_system.dto.LoginResponse;
import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.SupportTicket;
import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.service.AdminService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 🔐 ADMIN LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody AdminLoginRequest request) {
        return adminService.login(request.getUsername(), request.getPassword());
    }

    // 👥 USERS
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    // 🏦 ACCOUNTS
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return adminService.getAllAccounts();
    }

    // 💳 TRANSACTIONS
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return adminService.getAllTransactions();
    }

    // 🎫 SUPPORT TICKETS
    @GetMapping("/tickets")
    public List<SupportTicket> getAllTickets() {
        return adminService.getAllTickets();
    }
}