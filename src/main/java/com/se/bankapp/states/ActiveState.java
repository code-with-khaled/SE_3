package com.se.bankapp.states;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountState;

public class ActiveState implements AccountStateBehavior {
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

    @Override
    public void freeze(Account acc) {
        acc.setState(AccountState.FROZEN);
    }

    @Override
    public void suspend(Account acc) {
        acc.setState(AccountState.SUSPENDED);
    }

    @Override
    public void close(Account acc) {
        acc.setState(AccountState.CLOSED);
    }

    @Override
    public void activate(Account acc) {
        /* already active */
    }
}
