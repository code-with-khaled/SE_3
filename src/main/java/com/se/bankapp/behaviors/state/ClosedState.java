package com.se.bankapp.behaviors.state;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountState;

public class ClosedState implements AccountStateBehavior {
    @Override
    public void ensureAllowed(Account acc) {
        throw new IllegalStateException("Account is frozen, operations not allowed");
    }

    @Override
    public void freeze(Account acc) {
        acc.setState(AccountState.FROZEN);
    }

    @Override
    public void suspend(Account acc) {
        acc.setState(AccountState.SUSPENDED);
    }

    @Override
    public void close(Account acc) {
        /* already closed */
    }

    @Override
    public void activate(Account acc) {
        acc.setState(AccountState.ACTIVE);
    }
}
