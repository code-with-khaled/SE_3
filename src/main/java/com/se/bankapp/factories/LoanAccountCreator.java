package com.se.bankapp.factories;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.strategies.LoanInterest;

public class LoanAccountCreator implements AccountCreator {
    @Override
    public Account create(double balance) {
        Account acc = new Account(AccountType.LOAN, balance);
        acc.setInterestStrategy(new LoanInterest());
        return acc;
    }
}
