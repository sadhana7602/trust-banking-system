package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.Transaction;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.repository.AccountRepository;
import com.bank.Trust_banking_system.repository.TransactionRepository;
import com.bank.Trust_banking_system.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private String generateAccountNumber() {
        long number = (long) (Math.random() * 900000000000000L) + 100000000000000L;
        return String.valueOf(number);
    }

    // 🔹 GET ACCOUNT
    public Account getMyAccount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // 🔹 CREATE ACCOUNT
    public Account createAccount(Long userId,
                                 String accountType,
                                 String branchName) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (accountRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User already has an account");
        }

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(accountType)
                .branchName(branchName)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Account saved = accountRepository.save(account);

        emailService.sendMail(
                user.getEmail(),
                "Account Created",
                "Your account number: " + saved.getAccountNumber()
        );

        return saved;
    }

    // 🔹 DEPOSIT
    @Transactional
    public Account depositByUser(String email, BigDecimal amount) {

        Account account = getMyAccount(email);

        account.setBalance(account.getBalance().add(amount));

        // SAVE TRANSACTION
        saveTransaction(null, account.getAccountNumber(), amount, "DEPOSIT");

        emailService.sendMail(
                account.getUser().getEmail(),
                "Deposit Successful",
                "₹" + amount + " deposited. Balance: ₹" + account.getBalance()
        );

        return accountRepository.save(account);
    }

    // 🔹 WITHDRAW
    @Transactional
    public Account withdrawByUser(String email, BigDecimal amount) {

        Account account = getMyAccount(email);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        // SAVE TRANSACTION
        saveTransaction(account.getAccountNumber(), null, amount, "WITHDRAW");

        emailService.sendMail(
                account.getUser().getEmail(),
                "Withdrawal Successful",
                "₹" + amount + " withdrawn. Balance: ₹" + account.getBalance()
        );

        return accountRepository.save(account);
    }

    // 🔹 TRANSFER
    @Transactional
    public void transferByUser(String email, String toAccount, BigDecimal amount) {

        Account sender = getMyAccount(email);

        Account receiver = accountRepository.findByAccountNumber(toAccount)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // SAVE TRANSACTION
        saveTransaction(sender.getAccountNumber(), toAccount, amount, "TRANSFER");

        emailService.sendMail(sender.getUser().getEmail(),
                "Money Debited",
                "₹" + amount + " transferred to " + toAccount);

        emailService.sendMail(receiver.getUser().getEmail(),
                "Money Credited",
                "₹" + amount + " received from " + sender.getAccountNumber());
    }

    // 🔹 COMMON TRANSACTION LOGGER
    private void saveTransaction(String from,
                                 String to,
                                 BigDecimal amount,
                                 String type) {

        transactionRepository.save(Transaction.builder()
                .fromAccount(from)
                .toAccount(to)
                .amount(amount)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build());
    }
}