package com.se.bankapp.services;

import com.se.bankapp.models.ScheduledTransaction;
import com.se.bankapp.models.TransactionRecord;
import com.se.bankapp.repositories.ScheduledTransactionRepository;
import com.se.bankapp.repositories.TransactionRecordRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduledTransactionService {
    private final AccountService accountService;
    private final TransactionRecordRepository transactionRecordRepository;
    private final ScheduledTransactionRepository scheduledTransactionRepository;

    public ScheduledTransactionService(AccountService accountService, TransactionRecordRepository transactionRecordRepository, ScheduledTransactionRepository scheduledTransactionRepository) {
        this.accountService = accountService;
        this.transactionRecordRepository = transactionRecordRepository;
        this.scheduledTransactionRepository = scheduledTransactionRepository;
    }

    // Run every 3 seconds
    @Scheduled(fixedRate = 3000)
    public void processScheduledTransactions() {
        Instant now = Instant.now();
        List<ScheduledTransaction> due = scheduledTransactionRepository.findByScheduledAtBeforeAndExecutedFalseAndCancelledFalse(now);

        for (ScheduledTransaction st : due) {

            try {
                // Perform Transaction
                switch (st.getType()) {
                    case "DEPOSIT" -> accountService.deposit(st.getAccountId(), st.getAmount());
                    case "WITHDRAW" -> accountService.withdraw(st.getAccountId(), st.getAmount());
                }
            } catch(Exception e) {
                // log a failed transaction
                transactionRecordRepository.save(new TransactionRecord(
                        st.getAccountId(),
                        "FAILED: " + e.toString(),
                        st.getAmount()
                ));
            }

            if (st.isRecurring()) {
                // reschedule for next interval
                st.setScheduledAt(st.getScheduledAt().plusSeconds(st.getIntervalSeconds()));
            } else {
                st.setExecuted(true);
            }
            scheduledTransactionRepository.save(st);
        }
    }

    public ScheduledTransaction scheduleTransaction(ScheduledTransaction st) {
        return scheduledTransactionRepository.save(st);
    }

    public void cancelScheduledTransaction(Long id) {
        ScheduledTransaction st = scheduledTransactionRepository.findById(id)
                                                                .orElseThrow(() -> new NoSuchElementException("Scheduled transaction not found"));
        st.setCancelled(true);

        scheduledTransactionRepository.save(st);
    }
}
