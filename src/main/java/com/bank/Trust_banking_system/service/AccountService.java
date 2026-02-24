package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.repository.AccountRepository;
import com.bank.Trust_banking_system.repository.TransactionRepository;
import com.bank.Trust_banking_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final EmailService emailService;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository,
                          TransactionRepository transactionRepository,
                          EmailService emailService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.emailService = emailService;
    }

    // 🔹 CREATE ACCOUNT
    public Account createAccount(Long userId,
                                 String accountType,
                                 String branchName,
                                 String ifscCode) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .accountType(accountType)
                .branchName(branchName)
                .ifscCode(ifscCode)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return accountRepository.save(account);
    }

    // 🔹 GET ACCOUNT
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // 🔹 DEPOSIT
    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {

        Account account = getAccount(accountNumber);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        transactionRepository.save(Transaction.builder()
                .fromAccount(accountNumber)
                .toAccount(accountNumber)
                .amount(amount)
                .type("DEPOSIT")
                .createdAt(LocalDateTime.now())
                .build());

        emailService.sendMail(
                account.getUser().getEmail(),
                "Money Deposited",
                "₹" + amount + " deposited. Balance: ₹" + account.getBalance()
        );

        return account;
    }

    // 🔹 WITHDRAW
    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {

        Account account = getAccount(accountNumber);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        transactionRepository.save(Transaction.builder()
                .fromAccount(accountNumber)
                .toAccount(accountNumber)
                .amount(amount)
                .type("WITHDRAW")
                .createdAt(LocalDateTime.now())
                .build());

        emailService.sendMail(
                account.getUser().getEmail(),
                "Money Withdrawn",
                "₹" + amount + " withdrawn. Balance: ₹" + account.getBalance()
        );

        return account;
    }

    // 🔹 TRANSFER
    @Transactional
    public void transfer(String fromAccount,
                         String toAccount,
                         BigDecimal amount) {

        Account sender = getAccount(fromAccount);
        Account receiver = getAccount(toAccount);

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        transactionRepository.save(Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .type("TRANSFER")
                .createdAt(LocalDateTime.now())
                .build());

        emailService.sendMail(
                sender.getUser().getEmail(),
                "Money Debited",
                "₹" + amount + " transferred to " + toAccount +
                        ". Balance: ₹" + sender.getBalance()
        );

        emailService.sendMail(
                receiver.getUser().getEmail(),
                "Money Credited",
                "₹" + amount + " received from " + fromAccount +
                        ". Balance: ₹" + receiver.getBalance()
        );
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}