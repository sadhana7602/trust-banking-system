package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.CreateAccountRequest;
import com.bank.Trust_banking_system.dto.TransferRequest;
import com.bank.Trust_banking_system.dto.TransactionRequest;
import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.service.AccountService;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    // 🔹 GET LOGGED IN USER ACCOUNT
    @GetMapping("/my-account")
    public Account getMyAccount(Authentication authentication) {

        String email = (String) authentication.getPrincipal();

        return accountService.getMyAccount(email);
    }
    // CREATE ACCOUNT
    @PostMapping("/create")
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(
                request.getUserId(),
                request.getAccountType(),
                request.getBranchName()
        );
    }

    // DEPOSIT
    @PostMapping("/deposit")
    public Account deposit(@Valid @RequestBody TransactionRequest request) {
        return accountService.deposit(
                request.getAccountNumber(),
                request.getAmount()
        );
    }

    // WITHDRAW
    @PostMapping("/withdraw")
    public Account withdraw(@Valid @RequestBody TransactionRequest request) {
        return accountService.withdraw(
                request.getAccountNumber(),
                request.getAmount()
        );
    }

    // TRANSFER
    @PostMapping("/transfer")
    public String transfer(@Valid @RequestBody TransferRequest request) {
        accountService.transfer(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );
        return "Transfer successful";
    }
}