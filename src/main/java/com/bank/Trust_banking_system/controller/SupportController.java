package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.CommentRequest;
import com.bank.Trust_banking_system.dto.TicketRequest;
import com.bank.Trust_banking_system.entity.SupportTicket;
import com.bank.Trust_banking_system.entity.TicketComment;
import com.bank.Trust_banking_system.service.SupportService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    // 👤 USER CREATE TICKET
    @PostMapping("/create")
    public SupportTicket createTicket(@RequestBody TicketRequest request) {
        return supportService.createTicket(request);
    }

    // 👤 USER VIEW THEIR TICKETS
    @GetMapping("/my-tickets/{userId}")
    public List<SupportTicket> getUserTickets(@PathVariable Long userId) {
        return supportService.getUserTickets(userId);
    }

    // 👨‍💼 ADMIN VIEW ALL TICKETS
    @GetMapping("/all")
    public List<SupportTicket> getAllTickets() {
        return supportService.getAllTickets();
    }

    // 👨‍💼 ADMIN UPDATE STATUS
    @PutMapping("/status/{ticketId}")
    public SupportTicket updateStatus(@PathVariable Long ticketId,
                                      @RequestParam String status) {
        return supportService.updateStatus(ticketId, status);
    }

    // COMMENT ADD (ADMIN OR USER)
    @PostMapping("/comment")
    public TicketComment addComment(@RequestBody CommentRequest request) {
        return supportService.addComment(request);
    }

    // GET COMMENTS
    @GetMapping("/comments/{ticketId}")
    public List<TicketComment> getComments(@PathVariable Long ticketId) {
        return supportService.getComments(ticketId);
    }
}