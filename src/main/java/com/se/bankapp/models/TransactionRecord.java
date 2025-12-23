package com.se.bankapp.models;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(indexes = {
        @Index(name = "idx_account_id", columnList = "accountId"),
        @Index(name = "idx_created_at", columnList = "createdAt")
})
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

    // --- Getters ---
    public Long getId() { return id; }
    public Long getAccountId() { return accountId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }

    // --- Setters ---
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
