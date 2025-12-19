package com.se.bankapp.repositories;

import com.se.bankapp.models.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
