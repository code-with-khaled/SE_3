package com.se.bankapp.behaviors.type;

import com.se.bankapp.models.Account;

public interface AccountTypeBehavior {
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);
}
