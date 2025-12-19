package com.se.bankapp.factories;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.strategies.InvestmentInterest;

public class InvestmentAccountCreator implements AccountCreator {
    @Override
    public Account create(double balance) {
        Account acc = new Account(AccountType.INVESTMENT, balance);
        acc.setInterestStrategy(new InvestmentInterest());
        return acc;
    }
}
