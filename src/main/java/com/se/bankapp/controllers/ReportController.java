package com.se.bankapp.controllers;

import com.se.bankapp.services.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/daily")
    public String getDailyReport() {
        return service.getDailyReport();
    }

    @GetMapping("/summary")
    public String getAccountSummaryReport() {
        return service.getAccountSummaryReport();
    }
    @GetMapping("/audit")
    public String getAuditLog() {
        return service.getAuditLog();
    }
}
