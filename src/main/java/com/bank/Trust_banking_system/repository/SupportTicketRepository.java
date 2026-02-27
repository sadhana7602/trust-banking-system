package com.bank.Trust_banking_system.repository;

import com.bank.Trust_banking_system.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    // return tickets for a given user id (add if missing)
    List<SupportTicket> findByUserId(Long userId);
}