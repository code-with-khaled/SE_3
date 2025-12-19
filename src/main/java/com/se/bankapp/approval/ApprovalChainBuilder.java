package com.se.bankapp.approval;

public class ApprovalChainBuilder {
//    public static ApprovalHandler buildDepositChain() {
//        ApprovalHandler auto = new AutoApprovalHandler();
//        ApprovalHandler manger = new ManagerApprovalHandler();
//        auto.setNext(manger);
//        return auto;
//    }
//
//    public static ApprovalHandler buildWithdrawChain() {
//        ApprovalHandler auto = new AutoApprovalHandler();
//        ApprovalHandler manager = new ManagerApprovalHandler();
//        ApprovalHandler director = new DirectorApprovalHandler();
//        auto.setNext(manager).setNext(director);
//        return auto;
//    }
//
//    public static ApprovalHandler buildTransferChain() {
//        ApprovalHandler auto = new AutoApprovalHandler();
//        ApprovalHandler manager = new ManagerApprovalHandler();
//        ApprovalHandler director = new DirectorApprovalHandler();
//        auto.setNext(manager).setNext(director);
//        return auto;
//    }


    public static ApprovalHandler buildChain() {
        ApprovalHandler auto = new AutoApprovalHandler();
        ApprovalHandler manager = new ManagerApprovalHandler();
        ApprovalHandler director = new DirectorApprovalHandler();
        auto.setNext(manager).setNext(director);
        return auto;
    }
}
