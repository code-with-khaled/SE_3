package com.se.bankapp.services;

import com.se.bankapp.templates.AccountSummaryReport;
import com.se.bankapp.templates.AuditLogReport;
import com.se.bankapp.templates.DailyTransactionReport;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final DailyTransactionReport dailyTransactionReport;
    private final AccountSummaryReport accountSummaryReport;
    private final AuditLogReport auditLogReport;

    public ReportService(DailyTransactionReport dailyTransactionReport, AccountSummaryReport accountSummaryReport, AuditLogReport auditLogReport) {
        this.dailyTransactionReport = dailyTransactionReport;
        this.accountSummaryReport = accountSummaryReport;
        this.auditLogReport = auditLogReport;
    }

    // Get daily report
    @Cacheable(value = "dailyReports", key = "T(java.time.LocalDate).now()")
    public String getDailyReport() {
        System.out.println("Generating daily report...");
        return dailyTransactionReport.generateReport();
    }

    // Get account summary report
    public String getAccountSummaryReport() {
        return accountSummaryReport.generateReport();
    }

    // Get audit log
    public String getAuditLog() {
        return auditLogReport.generateReport();
    }
}
