package com.se.bankapp.behaviors.state;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountState;

public class SuspendedState implements AccountStateBehavior {
    @Override
    public void ensureAllowed(Account acc) {
        throw new IllegalStateException("Account is suspended, operations not allowed");
    }

    @Override
    public void freeze(Account acc) {
        acc.setState(AccountState.FROZEN);
    }

    @Override
    public void suspend(Account acc) {
        /* already suspended */
    }

    @Override
    public void close(Account acc) {
        acc.setState(AccountState.CLOSED);
    }

    @Override
    public void activate(Account acc) {
        acc.setState(AccountState.ACTIVE);
    }
}
