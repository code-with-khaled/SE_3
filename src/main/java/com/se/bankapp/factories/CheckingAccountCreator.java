package com.se.bankapp.factories;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;

public class CheckingAccountCreator implements AccountCreator {
    @Override
    public Account create(double balance) {
        Account acc = new Account(AccountType.CHECKING, balance);
        acc.setInterestStrategy(null);
        return acc;
    }
}
