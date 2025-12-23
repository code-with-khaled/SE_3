package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.models.ScheduledTransaction;
import com.se.bankapp.models.TransactionRecord;
import com.se.bankapp.repositories.ScheduledTransactionRepository;
import com.se.bankapp.repositories.TransactionRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduledTransactionServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ScheduledTransactionService scheduledTransactionService;

    @Autowired
    private ScheduledTransactionRepository scheduledTransactionRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @Test
    void schedule_and_process_depositTransaction() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);

        ScheduledTransaction st = new ScheduledTransaction();
        st.setAccountId(acc.getId());
        st.setType("DEPOSIT");
        st.setAmount(50.0);
        st.setScheduledAt(Instant.now().minusSeconds(1)); // already due
        st.setRecurring(false);
        st.setExecuted(false);
        st.setCancelled(false);

        scheduledTransactionService.scheduleTransaction(st);

        // Manually trigger processing
        scheduledTransactionService.processScheduledTransactions();

        double balance = accountService.getBalance(acc.getId());
        assertEquals(150.0, balance, 0.0001);

        List<TransactionRecord> history = accountService.getHistory(acc.getId());
        assertTrue(history.stream().anyMatch(tx -> tx.getType().equals("DEPOSIT")));

        ScheduledTransaction updated = scheduledTransactionRepository.findById(st.getId()).orElseThrow();
        assertTrue(updated.isExecuted());
    }

    @Test
    void schedule_and_process_withdrawTransaction() {
        Account acc = accountService.create(AccountType.CHECKING, 200.0);

        ScheduledTransaction st = new ScheduledTransaction();
        st.setAccountId(acc.getId());
        st.setType("WITHDRAW");
        st.setAmount(40.0);
        st.setScheduledAt(Instant.now().minusSeconds(1));
        st.setRecurring(false);
        st.setExecuted(false);
        st.setCancelled(false);

        scheduledTransactionService.scheduleTransaction(st);

        scheduledTransactionService.processScheduledTransactions();

        double balance = accountService.getBalance(acc.getId());
        assertEquals(160.0, balance, 0.0001);

        List<TransactionRecord> history = accountService.getHistory(acc.getId());
        assertTrue(history.stream().anyMatch(tx -> tx.getType().equals("WITHDRAW")));

        ScheduledTransaction updated = scheduledTransactionRepository.findById(st.getId()).orElseThrow();
        assertTrue(updated.isExecuted());
    }

    @Test
    void recurringTransaction_reschedulesInsteadOfExecutingOnce() {
        Account acc = accountService.create(AccountType.SAVINGS, 300.0);

        ScheduledTransaction st = new ScheduledTransaction();
        st.setAccountId(acc.getId());
        st.setType("DEPOSIT");
        st.setAmount(20.0);
        st.setScheduledAt(Instant.now().minusSeconds(1));
        st.setRecurring(true);
        st.setIntervalSeconds(60);
        st.setExecuted(false);
        st.setCancelled(false);

        scheduledTransactionService.scheduleTransaction(st);

        scheduledTransactionService.processScheduledTransactions();

        ScheduledTransaction updated = scheduledTransactionRepository.findById(st.getId()).orElseThrow();
        assertFalse(updated.isExecuted()); // still recurring
        assertTrue(updated.getScheduledAt().isAfter(Instant.now())); // rescheduled forward
    }

    @Test
    void cancelTransaction_marksAsCancelled() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);

        ScheduledTransaction st = new ScheduledTransaction();
        st.setAccountId(acc.getId());
        st.setType("DEPOSIT");
        st.setAmount(30.0);
        st.setScheduledAt(Instant.now().plusSeconds(60));
        st.setRecurring(false);
        st.setExecuted(false);
        st.setCancelled(false);

        scheduledTransactionService.scheduleTransaction(st);

        scheduledTransactionService.cancelScheduledTransaction(st.getId());

        ScheduledTransaction cancelled = scheduledTransactionRepository.findById(st.getId()).orElseThrow();
        assertTrue(cancelled.isCancelled());
    }
}
