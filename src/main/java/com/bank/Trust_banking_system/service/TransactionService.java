package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.repository.AccountRepository;
import com.bank.Trust_banking_system.repository.TransactionRepository;
import com.bank.Trust_banking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getTransactionsForUserEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Account account = accountRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Account not found for user: " + email));
        // return all transactions for that account (deposits, withdrawals, transfers)
        return transactionRepository.findByAccount(account);
    }

    // add helper to record a transfer (two transaction rows: debit + credit)
    public void recordTransfer(Account fromAccount, Account toAccount, Double amount) {
        if (fromAccount == null || toAccount == null || amount == null) return;

        Transaction debitTx = new Transaction();
        debitTx.setAccount(fromAccount);
        debitTx.setType("TRANSFER_OUT");
        debitTx.setAmount(amount);
        debitTx.setDescription("Transfer to " + toAccount.getAccountNumber());
        transactionRepository.save(debitTx);

        Transaction creditTx = new Transaction();
        creditTx.setAccount(toAccount);
        creditTx.setType("TRANSFER_IN");
        creditTx.setAmount(amount);
        creditTx.setDescription("Transfer from " + fromAccount.getAccountNumber());
        transactionRepository.save(creditTx);
    }

    // ...existing code...
}