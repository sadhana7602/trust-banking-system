package com.bank.Trust_banking_system.controller;




import com.bank.Trust_banking_system.dto.CommentRequest;

import com.bank.Trust_banking_system.dto.TicketRequest;

import com.bank.Trust_banking_system.entity.SupportTicket;

import com.bank.Trust_banking_system.entity.TicketComment;

import com.bank.Trust_banking_system.service.SupportService;




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




    // 🟢 CREATE TICKET

    @PostMapping("/create")

    public ResponseEntity<SupportTicket> createTicket(@RequestBody TicketRequest request) {

        return ResponseEntity.ok(supportService.createTicket(request));

    }




    // 🟢 USER TICKETS

    @GetMapping("/user/{userId}")

    public ResponseEntity<List<SupportTicket>> getUserTickets(@PathVariable Long userId) {

        return ResponseEntity.ok(supportService.getUserTickets(userId));

    }




    // 🟢 ADMIN ALL TICKETS

    @GetMapping("/all")

    public ResponseEntity<List<SupportTicket>> getAllTickets() {

        return ResponseEntity.ok(supportService.getAllTickets());

    }




    // 🟢 UPDATE STATUS

    @PutMapping("/status/{ticketId}")

    public ResponseEntity<SupportTicket> updateStatus(

            @PathVariable Long ticketId,

            @RequestParam String status) {

        return ResponseEntity.ok(supportService.updateStatus(ticketId, status));

    }




    // 🟢 ADD COMMENT

    @PostMapping("/comment")

    public ResponseEntity<TicketComment> addComment(@RequestBody CommentRequest request) {

        return ResponseEntity.ok(supportService.addComment(request));

    }




    // 🟢 GET COMMENTS

    @GetMapping("/comments/{ticketId}")

    public ResponseEntity<List<TicketComment>> getComments(@PathVariable Long ticketId) {

        return ResponseEntity.ok(supportService.getComments(ticketId));

    }

}