package com.bank.Trust_banking_system.service;

import com.bank.Trust_banking_system.dto.CommentRequest;
import com.bank.Trust_banking_system.dto.TicketRequest;
import com.bank.Trust_banking_system.entity.SupportTicket;
import com.bank.Trust_banking_system.entity.TicketComment;
import com.bank.Trust_banking_system.entity.User;
import com.bank.Trust_banking_system.repository.SupportTicketRepository;
import com.bank.Trust_banking_system.repository.TicketCommentRepository;
import com.bank.Trust_banking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupportService {

    private final SupportTicketRepository ticketRepo;
    private final TicketCommentRepository commentRepo;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public SupportService(SupportTicketRepository ticketRepo,
                          TicketCommentRepository commentRepo,
                          UserRepository userRepository,
                          EmailService emailService) {
        this.ticketRepo = ticketRepo;
        this.commentRepo = commentRepo;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public SupportTicket createTicket(TicketRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SupportTicket ticket = SupportTicket.builder()
                .subject(request.getSubject())
                .description(request.getDescription())
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        SupportTicket saved = ticketRepo.save(ticket);

        emailService.sendMail(
                "New Support Ticket Created",
                "Ticket: " + saved.getSubject(),
                "Ticket registered Successfully"
        );

        return saved;
    }

    public List<SupportTicket> getUserTickets(Long userId) {
        return ticketRepo.findByUserId(userId);
    }

    public List<SupportTicket> getAllTickets() {
        return ticketRepo.findAll();
    }

    public SupportTicket updateStatus(Long ticketId, String status) {

        SupportTicket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);
        SupportTicket updated = ticketRepo.save(ticket);

        emailService.sendMail(
                "Ticket Status Updated",
                "Ticket " + ticketId + " status changed to " + status,
                "Status has been updated for yout Ticket"
        );

        return updated;
    }

    public TicketComment addComment(CommentRequest request) {

        SupportTicket ticket = ticketRepo.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketComment comment = TicketComment.builder()
                .commentText(request.getCommentText())
                .commentedBy(request.getCommentedBy())
                .createdAt(LocalDateTime.now())
                .ticket(ticket)
                .build();

        return commentRepo.save(comment);
    }

    public List<TicketComment> getComments(Long ticketId) {
        return commentRepo.findByTicketId(ticketId);
    }
}