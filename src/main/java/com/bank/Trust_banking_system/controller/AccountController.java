package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.CreateAccountRequest;
import com.bank.Trust_banking_system.dto.TransferRequest;
import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.service.AccountService;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/my-account")
    public Account getMyAccount(Authentication authentication) {

        String email = authentication.getName(); // from JWT
        return accountService.getMyAccount(email);
    }

    // 🔹 CREATE ACCOUNT
    @PostMapping("/create")
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(
                request.getUserId(),
                request.getAccountType(),
                request.getBranchName()
        );
    }

    // 🔹 DEPOSIT (uses logged-in user)
    @PostMapping("/deposit")
    public Account deposit(Authentication auth,
                           @RequestParam BigDecimal amount) {

        String email = auth.getName();
        return accountService.depositByUser(email, amount);
    }

    // 🔹 WITHDRAW (uses logged-in user)
    @PostMapping("/withdraw")
    public Account withdraw(Authentication auth,
                            @RequestParam BigDecimal amount) {

        String email = auth.getName();
        return accountService.withdrawByUser(email, amount);
    }

    // 🔹 TRANSFER (from logged-in user)
    @PostMapping("/transfer")
    public String transfer(Authentication auth,
                           @Valid @RequestBody TransferRequest request) {

        String email = auth.getName();

        accountService.transferByUser(
                email,
                request.getToAccount(),
                request.getAmount()
        );

        return "Transfer successful";
    }
}