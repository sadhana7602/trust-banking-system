package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.repository.AccountRepository;
import com.bank.Trust_banking_system.repository.TransactionRepository;
import com.bank.Trust_banking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository,
                              AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getMyTransactions(String email) {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return transactionRepository
                .findByFromAccountOrToAccountOrderByCreatedAtDesc(
                        account.getAccountNumber(),
                        account.getAccountNumber()
                );
    }
}