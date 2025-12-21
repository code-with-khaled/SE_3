package com.se.bankapp.controllers;

import com.se.bankapp.models.ScheduledTransaction;
import com.se.bankapp.services.ScheduledTransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduled")
public class ScheduledTransactionController {
    private final ScheduledTransactionService service;

    public ScheduledTransactionController(ScheduledTransactionService service) {
        this.service = service;
    }

    // Create Scheduled transaction
    @PostMapping
    public ScheduledTransaction schedule(@RequestBody ScheduledTransaction st) {
        return service.scheduleTransaction(st);
    }

    // Cancel Scheduled transaction
    @PutMapping("/{id}/cancel")
    public void cancelScheduledTransaction(@PathVariable Long id) {
        service.cancelScheduledTransaction(id);
    }
}
