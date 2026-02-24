package com.bank.Trust_banking_system.dto;

import lombok.Data;

@Data
public class TicketRequest {
    private Long userId;
    private String subject;
    private String description;
}