package com.se.bankapp.facade;

import com.se.bankapp.models.*;
import com.se.bankapp.services.AccountService;
import com.se.bankapp.services.AccountGroupService;
import com.se.bankapp.services.SupportService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankingFacade {

    private final AccountService accountService;
    private final AccountGroupService groupService;
    private final SupportService supportService;

    public BankingFacade(AccountService accountService, AccountGroupService groupService, SupportService supportService) {
        this.accountService = accountService;
        this.groupService = groupService;
        this.supportService = supportService;
    }

    // Account operations
    public Account createAccount(AccountType type, double balance) {
        return accountService.create(type, balance);
    }

    public double getBalance(long id) {
        return accountService.getBalance(id);
    }

    public Account deposit(long id, double amount) {
        return accountService.deposit(id, amount);
    }

    public Account withdraw(long id, double amount) {
        return accountService.withdraw(id, amount);
    }

    public void transfer(long fromId, long toId, double amount) {
        accountService.transfer(fromId, toId, amount);
    }

    public Account freeze(long id) {
        return accountService.freezeAccount(id);
    }

    public Account suspend(long id) {
        return accountService.suspendAccount(id);
    }

    public Account activate(long id) {
        return accountService.activateAccount(id);
    }

    public Account close(long id) {
        return accountService.closeAccount(id);
    }

    public double calculateInterest(long id) {
        return accountService.calculateInterest(id);
    }

    public Account upgradeToPremium(long id) {
        return accountService.upgradeToPremium(id);
    }

    public List<TransactionRecord> getHistory(long id) {
        return accountService.getHistory(id);
    }

    // Group operations
    public AccountGroup createGroup(String name, List<Long> accountIds) {
        return groupService.createGroup(name, accountIds);
    }

    public List<Account> getGroupMembers(Long groupId) {
        return groupService.getMembers(groupId);
    }

    public List<Account> depositGroup(Long groupId, double amount) {
        return groupService.depositToGroup(groupId, amount);
    }

    public List<Account> withdrawGroup(Long groupId, double amount) {
        return groupService.withdrawFromGroup(groupId, amount);
    }

    // Ticket operations
    public SupportTicket createTicket(Long accountId, String subject, String description) {
        return supportService.createTicket(accountId, subject, description);
    }

    public List<SupportTicket> getTickets(Long accountId) {
        return supportService.getTicketsByAccount(accountId);
    }

    public SupportTicket advanceTicket(Long ticketId) {
        return supportService.advanceTicket(ticketId);
    }
}
