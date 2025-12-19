package com.se.bankapp.controllers;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.models.TransactionRecord;
import com.se.bankapp.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // Create account
    @PostMapping
    public Account create(@RequestParam String type, @RequestParam double balance) {
        AccountType accountType = AccountType.valueOf(type.toUpperCase());
        return service.create(accountType, balance);
    }

    // Get balance
    @GetMapping("/{id}/balance")
    public double getBalance(@PathVariable long id) {
        return service.getBalance(id);
    }

    // Deposit
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable long id, @RequestParam double amount) {
        return service.deposit(id, amount);
    }

    // Withdraw
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable long id, @RequestParam double amount) {
        return service.withdraw(id, amount);
    }

    // Transfer
    @PostMapping("/transfer")
    public void transfer(@RequestParam long fromId, @RequestParam long toId, @RequestParam double amount) {
        service.transfer(fromId, toId, amount);
    }

    // Freeze account
    @PostMapping("/{id}/freeze")
    public Account freeze(@PathVariable long id){
        return service.freezeAccount(id);
    }

    // Suspend account
    @PostMapping("/{id}/suspend")
    public Account suspend(@PathVariable long id) {
        return service.suspendAccount(id);
    }

    // Activate account
    @PostMapping("/{id}/activate")
    public Account activate(@PathVariable long id){
        return service.activateAccount(id);
    }

    // Close account
    @PostMapping("/{id}/close")
    public Account close(@PathVariable long id){
        return service.closeAccount(id);
    }

    // Get interest
    @GetMapping("/{id}/interest")
    public double interest(@PathVariable long id) {
        return service.calculateInterest(id);
    }

    // Upgrade to premium
    @PostMapping("/{id}/upgrade")
    public Account upgrade(@PathVariable long id){
        return service.upgradeToPremium(id);
    }

    // Get history
    @GetMapping("/{id}/transactions")
    public List<TransactionRecord> history(@PathVariable long id) {
        return service.getHistory(id);
    }
}
