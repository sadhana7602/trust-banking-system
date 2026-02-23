package com.bank.Trust_banking_system.controller;



import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create/{userId}")
    public Account createAccount(@PathVariable Long userId) {
        return accountService.createAccount(userId);
    }

    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestParam String accountNumber,
                           @RequestParam BigDecimal amount) {
        return accountService.deposit(accountNumber, amount);
    }

    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam String accountNumber,
                            @RequestParam BigDecimal amount) {
        return accountService.withdraw(accountNumber, amount);
    }
}
