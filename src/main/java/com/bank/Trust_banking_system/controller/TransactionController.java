package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.service.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/my")
    public List<Transaction> getMyTransactions(Authentication auth) {
        String email = auth.getName();
        return transactionService.getMyTransactions(email);
    }
}