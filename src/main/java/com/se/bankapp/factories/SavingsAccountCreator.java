package com.se.bankapp.factories;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.strategies.SavingsInterest;

public class SavingsAccountCreator implements AccountCreator {
    @Override
    public Account create(double balance) {
        Account acc = new Account(AccountType.SAVINGS, balance);
        acc.setInterestStrategy(new SavingsInterest());
        return acc;
    }
}
