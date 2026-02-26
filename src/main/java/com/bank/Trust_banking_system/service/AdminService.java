package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.dto.LoginResponse;
import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.SupportTicket;
import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.repository.AccountRepository;
import com.bank.Trust_banking_system.repository.SupportTicketRepository;
import com.bank.Trust_banking_system.repository.TransactionRepository;
import com.bank.Trust_banking_system.repository.UserRepository;
import com.bank.Trust_banking_system.security.JwtUtil;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final SupportTicketRepository ticketRepository;

    public AdminService(JwtUtil jwtUtil,
                        UserRepository userRepository,
                        AccountRepository accountRepository,
                        TransactionRepository transactionRepository,
                        SupportTicketRepository ticketRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.ticketRepository = ticketRepository;
    }

    // 🔐 ADMIN LOGIN (NO DB)
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

    // 📊 DASHBOARD DATA
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<SupportTicket> getAllTickets() {
        return ticketRepository.findAll();
    }
}