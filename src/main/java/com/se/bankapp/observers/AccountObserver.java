package com.se.bankapp.observers;

import com.se.bankapp.models.Account;

public interface AccountObserver {
    void update(Account account, String action, double amount);
}
