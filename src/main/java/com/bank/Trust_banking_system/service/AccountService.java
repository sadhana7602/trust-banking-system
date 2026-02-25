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

    // 🔹 GET ACCOUNT BY USER EMAIL
    public Account getMyAccount(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("ACCOUNT_NOT_FOUND"));
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

        String accountNumber = generateAccountNumber();

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .branchName(branchName)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Account saved = accountRepository.save(account);

        String body = "Hello " + user.getFullName() + ",\n\n"
                + "Your bank account has been created successfully.\n"
                + "Account Number: " + saved.getAccountNumber() + "\n\n"
                + "Thank you,\nTrust Banking System";

        emailService.sendMail(user.getEmail(), "Account Created", body);

        return saved;
    }

    // 🔹 DEPOSIT BY USER
    @Transactional
    public Account depositByUser(String email, BigDecimal amount) {

        Account account = getMyAccount(email);

        account.setBalance(account.getBalance().add(amount));

        transactionRepository.save(Transaction.builder()
                .toAccount(account.getAccountNumber())
                .amount(amount)
                .type("DEPOSIT")
                .createdAt(LocalDateTime.now())
                .build());

        emailService.sendMail(
                account.getUser().getEmail(),
                "Deposit Successful",
                "₹" + amount + " deposited. Balance: ₹" + account.getBalance()
        );

        return accountRepository.save(account);
    }

    // 🔹 WITHDRAW BY USER
    @Transactional
    public Account withdrawByUser(String email, BigDecimal amount) {

        Account account = getMyAccount(email);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        transactionRepository.save(Transaction.builder()
                .fromAccount(account.getAccountNumber())
                .amount(amount)
                .type("WITHDRAW")
                .createdAt(LocalDateTime.now())
                .build());

        emailService.sendMail(
                account.getUser().getEmail(),
                "Withdrawal Successful",
                "₹" + amount + " withdrawn. Balance: ₹" + account.getBalance()
        );

        return accountRepository.save(account);
    }

    // 🔹 TRANSFER BY USER
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

        transactionRepository.save(Transaction.builder()
                .fromAccount(sender.getAccountNumber())
                .toAccount(toAccount)
                .amount(amount)
                .type("TRANSFER")
                .createdAt(LocalDateTime.now())
                .build());

        emailService.sendMail(sender.getUser().getEmail(),
                "Money Debited",
                "₹" + amount + " transferred to " + toAccount);

        emailService.sendMail(receiver.getUser().getEmail(),
                "Money Credited",
                "₹" + amount + " received from " + sender.getAccountNumber());
    }
}