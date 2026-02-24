package com.bank.Trust_banking_system.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long ticketId;
    private String commentText;
    private String commentedBy; // USER or ADMIN
}