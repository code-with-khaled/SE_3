package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.models.TransactionRecord;
import com.se.bankapp.repositories.TransactionRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRecordRepository txRepo;

    @Test
    void dailyReport_includesTodaysTransactions() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);

        TransactionRecord tx = new TransactionRecord(acc.getId(), "DEPOSIT", 50.0);
        tx.setCreatedAt(Instant.now()); // ensure it's within today's range
        txRepo.save(tx);

        String report = reportService.getDailyReport();

        assertNotNull(report);
        assertTrue(report.contains("Daily Transactions Report"));
        assertTrue(report.contains("DEPOSIT"));
        assertTrue(report.contains("amount=50.0"));
    }

    @Test
    void accountSummaryReport_includesAccountDetails() {
        Account acc = accountService.create(AccountType.CHECKING, 200.0);
        acc.setPremium(true);

        String report = reportService.getAccountSummaryReport();

        assertNotNull(report);
        assertTrue(report.contains("Account Summery Report"));
        assertTrue(report.contains("Account#" + acc.getId()));
        assertTrue(report.contains("balance=" + acc.getBalance()));
        assertTrue(report.contains("isPremium=true"));
    }

    @Test
    void auditLogReport_includesTransactionHistory() {
        Account acc = accountService.create(AccountType.SAVINGS, 300.0);

        TransactionRecord tx = new TransactionRecord(acc.getId(), "WITHDRAW", 100.0);
        tx.setCreatedAt(Instant.now());
        txRepo.save(tx);

        String report = reportService.getAuditLog();

        assertNotNull(report);
        assertTrue(report.contains("Audit Log Report"));
        assertTrue(report.contains("WITHDRAW"));
        assertTrue(report.contains("amount=100.0"));
    }

    @Test
    void reports_returnHeaderEvenWhenEmpty() {
        String daily = reportService.getDailyReport();
        String summary = reportService.getAccountSummaryReport();
        String audit = reportService.getAuditLog();

        assertTrue(daily.contains("Daily Transactions Report"));
        assertTrue(summary.contains("Account Summery Report"));
        assertTrue(audit.contains("Audit Log Report"));
    }
}
