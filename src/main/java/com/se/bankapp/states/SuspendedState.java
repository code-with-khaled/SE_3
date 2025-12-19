package com.se.bankapp.states;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountState;

public class SuspendedState implements AccountStateBehavior {
    @Override
    public void deposit(Account account, double amount) {
        throw new IllegalStateException("Deposits not allowed on suspended accounts");
    }

    @Override
    public void withdraw(Account account, double amount) {
        throw new IllegalStateException("Withdrawals not allowed on suspended accounts");
    }

    @Override
    public void freeze(Account acc) {
        acc.setState(AccountState.FROZEN);
    }

    @Override
    public void suspend(Account acc) {
        /* already suspended */
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
