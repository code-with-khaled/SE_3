package com.se.bankapp.repositories;

import com.se.bankapp.models.ScheduledTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ScheduledTransactionRepository extends JpaRepository<ScheduledTransaction, Long> {
    List<ScheduledTransaction> findByScheduledAtBeforeAndExecutedFalseAndCancelledFalse(Instant now);
}
