package com.se.bankapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class ScheduledTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private String type; // e.g. CREATE, DEPOSIT, WITHDRAW
    private double amount;

    private Instant scheduledAt;
    private boolean isRecurring;
    private long intervalSeconds;

    private boolean executed = false;

    // --- Constructors ---
    public ScheduledTransaction() {}

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Instant getScheduledAt() {
        return scheduledAt;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public long getIntervalSeconds() {
        return intervalSeconds;
    }

    public boolean isExecuted() {
        return executed;
    }

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

    public void setScheduledAt(Instant scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public void setIntervalSeconds(long intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
