package com.bank.Trust_banking_system.repository;

import com.bank.Trust_banking_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountOrToAccountOrderByCreatedAtDesc(
            String fromAccount,
            String toAccount
    );
}