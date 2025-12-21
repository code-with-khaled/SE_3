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

    // Get daily reports
    @GetMapping("/daily")
    public String getDailyReport() {
        return service.getDailyReport();
    }

    // Get summary reports
    @GetMapping("/summary")
    public String getAccountSummaryReport() {
        return service.getAccountSummaryReport();
    }

    // Get audit log
    @GetMapping("/audit")
    public String getAuditLog() {
        return service.getAuditLog();
    }
}
