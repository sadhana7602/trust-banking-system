package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.CommentRequest;
import com.bank.Trust_banking_system.dto.TicketRequest;
import com.bank.Trust_banking_system.entity.SupportTicket;
import com.bank.Trust_banking_system.entity.TicketComment;
import com.bank.Trust_banking_system.service.SupportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping("/ticket")
    public SupportTicket createTicket(@RequestBody TicketRequest request) {
        return supportService.createTicket(request);
    }

    @GetMapping("/user/{userId}")
    public List<SupportTicket> getUserTickets(@PathVariable Long userId) {
        return supportService.getUserTickets(userId);
    }

    @PostMapping("/comment")
    public TicketComment addComment(@RequestBody CommentRequest request) {
        return supportService.addComment(request);
    }

    @GetMapping("/comments/{ticketId}")
    public List<TicketComment> getComments(@PathVariable Long ticketId) {
        return supportService.getComments(ticketId);
    }
}