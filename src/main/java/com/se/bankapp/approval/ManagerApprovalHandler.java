package com.se.bankapp.approval;

public class ManagerApprovalHandler extends ApprovalHandler {
    @Override
    protected boolean process(TransactionApprovalRequest request) {
        if (request.getAmount() <= 10000) {
            System.out.print("------------    ");
            System.out.print("Manager approved transaction: " + request.getAmount());
            System.out.println("    ------------");
            return true;
        }
        return false; // pass to next handler
    }
}
