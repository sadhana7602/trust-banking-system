package com.bank.Trust_banking_system.repository;


import com.bank.Trust_banking_system.entity.Account;
import com.bank.Trust_banking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByUserId(Long userId);

    // add this method so you can lookup account by User entity
    Optional<Account> findByUser(User user);
}
