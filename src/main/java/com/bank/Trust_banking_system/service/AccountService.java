package com.bank.Trust_banking_system.service;



import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.repository.AccountRepository;
import com.bank.Trust_banking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .balance(BigDecimal.ZERO)
                .user(user)
                .build();

        return accountRepository.save(account);
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account deposit(String accountNumber, BigDecimal amount) {

        Account account = getAccount(accountNumber);
        account.setBalance(account.getBalance().add(amount));

        return accountRepository.save(account);
    }

    public Account withdraw(String accountNumber, BigDecimal amount) {

        Account account = getAccount(accountNumber);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
}
