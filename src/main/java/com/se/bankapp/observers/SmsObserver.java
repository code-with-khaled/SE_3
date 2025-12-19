package com.se.bankapp.observers;

import com.se.bankapp.models.Account;

public class SmsObserver implements AccountObserver {
    @Override
    public void update(Account account, String action, double amount) {
        System.out.println("-----------------------------------------");
        System.out.println(
                "SMS: Account" + account.getId()
                        + " performed " + action
                        + " of " + amount);
    }
}
