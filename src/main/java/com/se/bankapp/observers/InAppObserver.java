package com.se.bankapp.observers;

import com.se.bankapp.models.Account;

public class InAppObserver implements AccountObserver {
    @Override
    public void update(Account account, String action, double amount) {
        System.out.println("-----------------------------------------");
        System.out.println(
                "In-App: Account" + account.getId()
                        + " performed " + action
                        + " of " + amount);
    }
}
