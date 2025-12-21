package com.se.bankapp.controllers;

import com.se.bankapp.facade.BankingFacade;
import com.se.bankapp.models.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankingFacade facade;

    public BankController(BankingFacade facade) {
        this.facade = facade;
    }

    // --- Accounts endpoints ---
    // Create account
    @PostMapping("/accounts")
    public Account createAccount(@RequestParam String type, @RequestParam double balance) {
        AccountType accountType = AccountType.valueOf(type.toUpperCase());
        return facade.createAccount(accountType, balance);
    }

    // Get balance
    @GetMapping("/accounts/{id}/balance")
    public double getBalance(@PathVariable long id) {
        return facade.getBalance(id);
    }

    // Deposit
    @PostMapping("/accounts/{id}/deposit")
    public Account deposit(@PathVariable long id, @RequestParam double amount) {
        return facade.deposit(id, amount);
    }

    // Withdraw
    @PostMapping("/accounts/{id}/withdraw")
    public Account withdraw(@PathVariable long id, @RequestParam double amount) {
        return facade.withdraw(id, amount);
    }

    // Transfer
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestParam long fromId, @RequestParam long toId, @RequestParam double amount) {
        facade.transfer(fromId, toId, amount);
    }

    // Freeze account
    @PostMapping("/accounts/{id}/freeze")
    public Account freeze(@PathVariable long id) {
        return facade.freeze(id);
    }

    // Suspend account
    @PostMapping("/accounts/{id}/suspend")
    public Account suspend(@PathVariable long id) {
        return facade.suspend(id);
    }

    // Activate account
    @PostMapping("/accounts/{id}/activate")
    public Account activate(@PathVariable long id) {
        return facade.activate(id);
    }

    // Close account
    @PostMapping("/accounts/{id}/close")
    public Account close(@PathVariable long id) {
        return facade.close(id);
    }

    // Get interest
    @GetMapping("/accounts/{id}/interest")
    public double interest(@PathVariable long id) {
        return facade.calculateInterest(id);
    }

    // Upgrade to premium
    @PostMapping("/accounts/{id}/upgrade")
    public Account upgrade(@PathVariable long id) {
        return facade.upgradeToPremium(id);
    }


    // Get history
    @GetMapping("/accounts/{id}/transactions")
    public List<TransactionRecord> history(@PathVariable long id) {
        return facade.getHistory(id);
    }

    // --- Group endpoints ---
    // Create group
    @PostMapping("/groups")
    public AccountGroup createGroup(@RequestParam String name, @RequestBody List<Long> accountIds) {
        return facade.createGroup(name, accountIds);
    }

    // Get group members
    @GetMapping("/groups/{groupId}/members")
    public List<Account> getMembers(@PathVariable Long groupId) {
        return facade.getGroupMembers(groupId);
    }

    // Deposit to group
    @PostMapping("/groups/{groupId}/deposit")
    public List<Account> depositGroup(@PathVariable Long groupId, @RequestParam double amount) {
        return facade.depositGroup(groupId, amount);
    }

    // Withdraw from group
    @PostMapping("/groups/{groupId}/withdraw")
    public List<Account> withdrawGroup(@PathVariable Long groupId, @RequestParam double amount) {
        return facade.withdrawGroup(groupId, amount);
    }

    // --- Tickets endpoints ---
    // Create ticket
    @PostMapping("/tickets")
    public SupportTicket createTicket(@RequestParam Long accountId, @RequestParam String subject, @RequestParam String description) {
        return facade.createTicket(accountId, subject, description);
    }

    // Get tickets
    @GetMapping("/tickets/{accountId}")
    public List<SupportTicket> getTickets(@PathVariable Long accountId) {
        return facade.getTickets(accountId);
    }

    // Advance ticket
    @PostMapping("/tickets/{ticketId}/advance")
    public SupportTicket advanceTicket(@PathVariable Long ticketId) {
        return facade.advanceTicket(ticketId);
    }

    // --- Scheduled transaction endpoints ---
    // Create Scheduled transaction
    @PostMapping("/scheduled")
    public ScheduledTransaction schedule(@RequestBody ScheduledTransaction st) {
        return facade.scheduleTransaction(st);
    }

    // Cancel Scheduled transaction
    @PutMapping("/scheduled/{id}/cancel")
    public void cancelScheduledTransaction(@PathVariable Long id) {
        facade.cancelScheduledTransaction(id);
    }

    // --- Report endpoints ---
    // Get daily reports
    @GetMapping("/reports/daily")
    public String getDailyReport() {
        return facade.getDailyReport();
    }

    // Get summary reports
    @GetMapping("/reports/summary")
    public String getAccountSummaryReport() {
        return facade.getAccountSummaryReport();
    }

    // Get audit log
    @GetMapping("/reports/audit")
    public String getAuditLog() {
        return facade.getAuditLog();
    }
}
