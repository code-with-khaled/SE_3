package com.se.bankapp.services;

import com.se.bankapp.models.AccountState;
import com.se.bankapp.states.*;

public class AccountStateFactory {
    public static AccountStateBehavior getBehavior(AccountState state) {
        return switch (state) {
            case ACTIVE -> new ActiveState();
            case FROZEN -> new FrozenState();
            case SUSPENDED -> new SuspendedState();
            case CLOSED -> new ClosedState();
        };
    }
}
