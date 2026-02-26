package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.service.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<Transaction>> getMyTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // your JWT config should populate username/email here
        List<Transaction> txs = transactionService.getTransactionsForUserEmail(email);
        return ResponseEntity.ok(txs);
    }
}