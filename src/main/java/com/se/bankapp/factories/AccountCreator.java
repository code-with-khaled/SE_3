package com.se.bankapp.factories;

import com.se.bankapp.models.Account;

public interface AccountCreator {
    Account create(double balance);
}
