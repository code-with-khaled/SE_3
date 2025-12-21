package com.se.bankapp.behaviors.state;

import com.se.bankapp.models.Account;

public interface AccountStateBehavior {
//    void deposit(Account account, double amount);
//    void withdraw(Account account, double amount);

    void ensureAllowed(Account acc);

    void freeze(Account acc);
    void suspend(Account acc);
    void close(Account acc);
    void activate(Account acc);
}
