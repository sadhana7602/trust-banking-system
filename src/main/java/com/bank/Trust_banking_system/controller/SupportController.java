package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.dto.CommentRequest;

import com.bank.Trust_banking_system.dto.TicketRequest;

import com.bank.Trust_banking_system.entity.SupportTicket;

import com.bank.Trust_banking_system.entity.TicketComment;

import com.bank.Trust_banking_system.service.SupportService;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/support")

public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {

        this.supportService = supportService;

    }

    @PostMapping("/create")

    public ResponseEntity<SupportTicket> createTicket(@RequestBody TicketRequest request) {

        SupportTicket created = supportService.createTicket(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    @GetMapping("/user/{userId}")

    public ResponseEntity<List<SupportTicket>> getUserTickets(@PathVariable Long userId) {

        List<SupportTicket> tickets = supportService.getTicketsForUser(userId);

        return ResponseEntity.ok(tickets);

    }

    @GetMapping("/all")

    public List<SupportTicket> getAllTickets() {

        return supportService.getAllTickets();

    }

    @PutMapping("/status/{ticketId}")

    public SupportTicket updateStatus(@PathVariable Long ticketId,

                                      @RequestParam String status) {

        return supportService.updateStatus(ticketId, status);

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