package com.se.bankapp.models;

import com.se.bankapp.models.TicketState;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_account_id", columnList = "accountId"),
})
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private String subject;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketState status; // OPEN, IN_PROGRESS, RESOLVED, CLOSED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SupportTicket() {
        this.status = TicketState.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    // --- GETTERS ---
    public Long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public TicketState getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // --- SETTERS ---
    public void setId(long id) {
        this.id = id;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // --- State Transition ---
    public void nextState() {
        this.status = this.status.next();
        this.updatedAt = LocalDateTime.now();
    }
}
