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

        //  MAIL TO USER
        String body = "Hello " + user.getFullName() + ",\n\n"
                + "Your support ticket has been created successfully.\n"
                + "Ticket Subject: " + saved.getSubject() + "\n"
                + "Status: OPEN\n\n"
                + "Our team will get back to you soon.\n\n"
                + "Regards,\nTrust Banking Support";

        emailService.sendMail(
                user.getEmail(),
                "Support Ticket Created",
                body
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

        User user = ticket.getUser();

        // 📧 MAIL WHEN STATUS UPDATED
        String body = "Hello " + user.getFullName() + ",\n\n"
                + "Your support ticket status has been updated.\n"
                + "Ticket Subject: " + ticket.getSubject() + "\n"
                + "New Status: " + status + "\n\n"
                + "Regards,\nTrust Banking Support";

        emailService.sendMail(
                user.getEmail(),
                "Ticket Status Updated",
                body
        );

        // 📧 EXTRA MAIL IF RESOLVED
        if ("RESOLVED".equalsIgnoreCase(status) || "CLOSED".equalsIgnoreCase(status)) {

            String closeBody = "Hello " + user.getFullName() + ",\n\n"
                    + "Your support ticket has been resolved successfully.\n"
                    + "If you have any further questions, feel free to contact us.\n\n"
                    + "Thank you for banking with us.\n\n"
                    + "Regards,\nTrust Banking Support";

            emailService.sendMail(
                    user.getEmail(),
                    "Ticket Closed",
                    closeBody
            );
        }

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

        TicketComment saved = commentRepo.save(comment);

        // 📧 MAIL USER ABOUT NEW COMMENT
        User user = ticket.getUser();

        String body = "Hello " + user.getFullName() + ",\n\n"
                + "There is a new update on your support ticket.\n"
                + "Comment: " + saved.getCommentText() + "\n\n"
                + "Regards,\nTrust Banking Support";

        emailService.sendMail(
                user.getEmail(),
                "New Comment on Your Ticket",
                body
        );

        return saved;
    }

    public List<TicketComment> getComments(Long ticketId) {
        return commentRepo.findByTicketId(ticketId);
    }
}