package com.se.bankapp.controllers;

import com.se.bankapp.models.ScheduledTransaction;
import com.se.bankapp.services.ScheduledTransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduled")
public class ScheduledTransactionController {
    private final ScheduledTransactionService service;

    public ScheduledTransactionController(ScheduledTransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ScheduledTransaction schedule(@RequestBody ScheduledTransaction st) {
        return service.scheduleTransaction(st);
    }
}
