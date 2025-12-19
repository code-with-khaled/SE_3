package com.se.bankapp.states;

import com.se.bankapp.models.Account;

public interface AccountStateBehavior {
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);

    void freeze(Account acc);
    void suspend(Account acc);
    void close(Account acc);
    void activate(Account acc);
}
