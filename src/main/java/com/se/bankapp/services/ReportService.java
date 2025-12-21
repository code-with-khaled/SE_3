package com.se.bankapp.services;

import com.se.bankapp.templates.AccountSummaryReport;
import com.se.bankapp.templates.AuditLogReport;
import com.se.bankapp.templates.DailyTransactionReport;
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

    public String getDailyReport() {
        return dailyTransactionReport.generateReport();
    }

    public String getAccountSummaryReport() {
        return accountSummaryReport.generateReport();
    }

    public String getAuditLog() {
        return auditLogReport.generateReport();
    }
}
