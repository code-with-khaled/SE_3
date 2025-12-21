package com.se.bankapp.behaviors.type;

import com.se.bankapp.models.Account;

public class InvestmentBehavior implements AccountTypeBehavior {
    @Override
    public void deposit(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
    }
}
