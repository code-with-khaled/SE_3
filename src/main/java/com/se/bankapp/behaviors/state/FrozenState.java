package com.se.bankapp.behaviors.state;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountState;

public class FrozenState implements AccountStateBehavior {
    @Override
    public void ensureAllowed(Account acc) {
        throw new IllegalStateException("Account is frozen, operations not allowed");
    }

    @Override
    public void freeze(Account acc) {
        /* already frozen */
    }

    @Override
    public void suspend(Account acc) {
        acc.setState(AccountState.SUSPENDED);
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
