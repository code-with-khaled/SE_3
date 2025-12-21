package com.se.bankapp.controllers;

import com.se.bankapp.models.SupportTicket;
import com.se.bankapp.services.SupportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/support")
public class SupportController {
    private final SupportService service;

    public SupportController(SupportService service) {
        this.service = service;
    }

    // Create ticket
    @PostMapping("/tickets")
    public SupportTicket createTicket(@RequestParam Long accountId, @RequestParam String subject, @RequestParam String description) {
        return service.createTicket(accountId, subject, description);
    }

    // Get tickets
    @GetMapping("/tickets/{accountId}")
    public List<SupportTicket> getTickets(@PathVariable Long accountId) {
        return service.getTicketsByAccount(accountId);
    }

    // Advance ticket
    @PostMapping("/tickets/{ticketId}/advance")
    public SupportTicket advanceTicket(@PathVariable Long ticketId) {
        return service.advanceTicket(ticketId);
    }
}
