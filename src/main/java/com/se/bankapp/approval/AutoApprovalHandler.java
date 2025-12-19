package com.se.bankapp.approval;

public class AutoApprovalHandler extends ApprovalHandler {
    @Override
    protected boolean process(TransactionApprovalRequest request) {
        if (request.getAmount() <= 1000) {
            System.out.print("------------    ");
            System.out.print("Auto-approved transaction: " + request.getAmount());
            System.out.println("    ------------");
            return true;
        }
        return false; // pass to next handler
    }
}
