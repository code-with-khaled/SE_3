package com.se.bankapp.strategies;

import com.se.bankapp.models.Account;

public class InvestmentInterest implements InterestStrategy {
    @Override
    public double calculateInterest(Account account) {
        return account.getBalance() * 0.07; // 7% interest
    }
}
