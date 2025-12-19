package com.se.bankapp.strategies;

import com.se.bankapp.models.Account;

public class LoanInterest implements InterestStrategy {
    @Override
    public double calculateInterest(Account account) {
        return account.getBalance() * 0.05; // 5% interest
    }
}
