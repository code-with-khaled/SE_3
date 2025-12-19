package com.se.bankapp.strategies;

import com.se.bankapp.models.Account;

public class SavingsInterest implements InterestStrategy {
    @Override
    public double calculateInterest(Account account) {
        return account.getBalance() * 0.03; // 3% interest
    }
}
