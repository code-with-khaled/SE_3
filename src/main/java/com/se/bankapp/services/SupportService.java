package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.SupportTicket;
import com.se.bankapp.repositories.AccountRepository;
import com.se.bankapp.repositories.SupportTicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupportService {
    private final SupportTicketRepository repo;
    private final AccountRepository accountRepository;
    private final NotificationService notificationService;

    public SupportService(SupportTicketRepository repo, AccountRepository accountRepository, NotificationService notificationService) {
        this.repo = repo;
        this.accountRepository = accountRepository;
        this.notificationService = notificationService;
    }

    public SupportTicket createTicket(Long accountId, String subject, String description) {
        Account acc = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));

        SupportTicket ticket = new SupportTicket();
        ticket.setAccountId(accountId);
        ticket.setSubject(subject);
        ticket.setDescription(description);
        ticket.setCreatedAt(LocalDateTime.now());

        notificationService.notify(acc, "SUPPORT_TICKET", 0);
        return repo.save(ticket);
    }

    public List<SupportTicket> getTicketsByAccount(Long accountId) {
        return repo.findByAccountId(accountId);
    }

    public SupportTicket advanceTicket(Long ticketId) {
        SupportTicket ticket = repo.findById(ticketId).orElseThrow(() -> new NoSuchElementException("Ticket not found"));
        Account acc = accountRepository.findById(ticket.getAccountId()).orElseThrow(() -> new NoSuchElementException("Account not found"));

        ticket.nextState();

        notificationService.notify(acc, "UPDATED_TICKET_STATE_TO_" + ticket.getStatus(), 0);
        return repo.save(ticket);
    }
}
