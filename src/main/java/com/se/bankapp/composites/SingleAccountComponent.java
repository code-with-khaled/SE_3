package com.se.bankapp.composites;

import com.se.bankapp.models.Account;
import com.se.bankapp.services.AccountService;

public class SingleAccountComponent implements AccountComponent {
    private final Account account;
    private final AccountService accountService;

    public SingleAccountComponent(Account account, AccountService accountService) {
        this.account = account;
        this.accountService = accountService;
    }

    @Override
    public double getBalance() {
        return accountService.getBalance(account.getId());
    }

    @Override
    public void deposit(double amount) {
        accountService.deposit(account.getId(), amount);
    }

    @Override
    public void withdraw(double amount) {
        accountService.withdraw(account.getId(), amount);
    }

    public Account getAccount() {
        return account;
    }
}
