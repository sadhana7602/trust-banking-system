package com.bank.Trust_banking_system.controller;

import com.bank.Trust_banking_system.entity.SupportTicket;
import com.bank.Trust_banking_system.service.SupportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final SupportService supportService;

    public AdminController(SupportService supportService) {
        this.supportService = supportService;
    }

    @GetMapping("/tickets")
    public List<SupportTicket> getAllTickets() {
        return supportService.getAllTickets();
    }

    @PutMapping("/ticket/{id}/status")
    public SupportTicket updateStatus(@PathVariable Long id,
                                      @RequestParam String status) {
        return supportService.updateStatus(id, status);
    }
}