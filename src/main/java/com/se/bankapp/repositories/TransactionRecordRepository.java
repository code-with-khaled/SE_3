package com.se.bankapp.repositories;

import com.se.bankapp.models.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByCreatedAtBetween(Instant start, Instant end);
    List<TransactionRecord> findByAccountId(Long accountId);
    List<TransactionRecord> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
