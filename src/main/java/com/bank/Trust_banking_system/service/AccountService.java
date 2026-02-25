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

    // 🔹 GET LOGGED IN USER ACCOUNT
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

        String accountNumber = generateAccountNumber();

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .branchName(branchName)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Account savedAccount = accountRepository.save(account);

        // 📧 SEND ACCOUNT CREATION EMAIL
        String body = "Hello " + user.getFullName() + ",\n\n"
                + "Your bank account has been created successfully.\n\n"
                + "Account Number: " + savedAccount.getAccountNumber() + "\n"
                + "Account Type: " + savedAccount.getAccountType() + "\n"
                + "Branch: " + savedAccount.getBranchName() + "\n\n"
                + "Thank you for choosing Trust Banking System.";

        emailService.sendMail(
                user.getEmail(),
                "Account Created Successfully 🎉",
                body
        );

        return savedAccount;
    }

    // 🔹 DEPOSIT
    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));

        transactionRepository.save(Transaction.builder()
                .toAccount(accountNumber)
                .amount(amount)
                .type("DEPOSIT")
                .createdAt(LocalDateTime.now())
                .build());

        String body = "Hello,\n\n"
                + "Amount ₹" + amount + " has been successfully deposited.\n"
                + "Account Number: " + accountNumber + "\n"
                + "Current Balance: ₹" + account.getBalance() + "\n\n"
                + "Thank you,\nTrust Banking System";

        emailService.sendMail(account.getUser().getEmail(),
                "Deposit Successful",
                body);

        return accountRepository.save(account);
    }

    // 🔹 WITHDRAW
    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        transactionRepository.save(Transaction.builder()
                .fromAccount(accountNumber)
                .amount(amount)
                .type("WITHDRAW")
                .createdAt(LocalDateTime.now())
                .build());

        String body = "Hello,\n\n"
                + "Amount ₹" + amount + " has been withdrawn.\n"
                + "Account Number: " + accountNumber + "\n"
                + "Remaining Balance: ₹" + account.getBalance() + "\n\n"
                + "Thank you,\nTrust Banking System";

        emailService.sendMail(account.getUser().getEmail(),
                "Withdrawal Successful",
                body);

        return accountRepository.save(account);
    }

    // 🔹 TRANSFER
    @Transactional
    public void transfer(String from, String to, BigDecimal amount) {

        Account sender = accountRepository.findByAccountNumber(from)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account receiver = accountRepository.findByAccountNumber(to)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        transactionRepository.save(Transaction.builder()
                .fromAccount(from)
                .toAccount(to)
                .amount(amount)
                .type("TRANSFER")
                .createdAt(LocalDateTime.now())
                .build());

        String senderBody = "Hello,\n\n"
                + "₹" + amount + " has been transferred from your account.\n"
                + "To Account: " + to + "\n"
                + "Remaining Balance: ₹" + sender.getBalance() + "\n\n"
                + "Thank you,\nTrust Banking System";

        String receiverBody = "Hello,\n\n"
                + "₹" + amount + " has been credited to your account.\n"
                + "From Account: " + from + "\n"
                + "Current Balance: ₹" + receiver.getBalance() + "\n\n"
                + "Thank you,\nTrust Banking System";

        emailService.sendMail(sender.getUser().getEmail(),
                "Money Debited",
                senderBody);

        emailService.sendMail(receiver.getUser().getEmail(),
                "Money Credited",
                receiverBody);
    }
}