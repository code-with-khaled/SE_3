package com.se.bankapp.approval;

public class DirectorApprovalHandler extends ApprovalHandler {
    @Override
    protected boolean process(TransactionApprovalRequest request) {
        if (request.getAmount() > 10000) {
            System.out.print("------------    ");
            System.out.print("Director approved transaction: " + request.getAmount());
            System.out.println("    ------------");
            return true;
        }
        return false;
    }
}
