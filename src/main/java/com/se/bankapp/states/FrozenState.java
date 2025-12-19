package com.se.bankapp.states;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountState;

public class FrozenState implements AccountStateBehavior {
    @Override
    public void deposit(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        throw new IllegalStateException("Withdrawals not allowed on frozen accounts");
    }

    @Override
    public void freeze(Account acc) {
        /* already frozen */
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
        acc.setState(AccountState.ACTIVE);
    }
}
