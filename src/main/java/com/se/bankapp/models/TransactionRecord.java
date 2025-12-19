package com.se.bankapp.models;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String type;   // e.g. CREATE, DEPOSIT, WITHDRAW

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public TransactionRecord() {}

    public TransactionRecord(Long accountId, String type, double amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }

    // Getters
    public Long getId() { return id; }
    public Long getAccountId() { return accountId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
}
