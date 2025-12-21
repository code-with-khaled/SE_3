package com.se.bankapp.behaviors.type;

import com.se.bankapp.models.Account;

public class LoanBehavior implements AccountTypeBehavior {
    @Override
    public void deposit(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed from loan accounts");
    }
}
