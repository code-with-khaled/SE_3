package com.se.bankapp.approval;

public abstract class ApprovalHandler {
    private ApprovalHandler next;

    public ApprovalHandler setNext(ApprovalHandler next) {
        this.next = next;
        return next;
    }

    public void handle(TransactionApprovalRequest request) {
        if (!process(request) && next != null) {
            next.handle(request);
        }
    }

    protected abstract boolean process(TransactionApprovalRequest request);
}
