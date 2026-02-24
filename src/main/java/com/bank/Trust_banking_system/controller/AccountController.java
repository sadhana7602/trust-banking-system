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

    // 🔹 CREATE ACCOUNT
    @PostMapping("/create")
    public Account createAccount(@RequestParam Long userId,
                                 @RequestParam String accountType,
                                 @RequestParam String branchName,
                                 @RequestParam String ifscCode) {

        return accountService.createAccount(userId, accountType, branchName, ifscCode);
    }

    // 🔹 GET ACCOUNT
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    // 🔹 DEPOSIT
    @PostMapping("/deposit")
    public Account deposit(@RequestParam String accountNumber,
                           @RequestParam BigDecimal amount) {
        return accountService.deposit(accountNumber, amount);
    }

    // 🔹 WITHDRAW
    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam String accountNumber,
                            @RequestParam BigDecimal amount) {
        return accountService.withdraw(accountNumber, amount);
    }

    // 🔹 TRANSFER
    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccount,
                           @RequestParam String toAccount,
                           @RequestParam BigDecimal amount) {

        accountService.transfer(fromAccount, toAccount, amount);
        return "Transfer successful";
    }
}