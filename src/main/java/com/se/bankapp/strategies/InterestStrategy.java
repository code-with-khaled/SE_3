package com.se.bankapp.strategies;

import com.se.bankapp.models.Account;

public interface InterestStrategy {
    double calculateInterest(Account account);
}
