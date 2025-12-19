package com.se.bankapp.approval;

public class TransactionApprovalRequest {
    private final long accountId;
    private final double amount;
    private final String type;

    public TransactionApprovalRequest(long accountId, double amount, String type) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
    }

    public long getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
}
