package com.se.bankapp.decorators;

import com.se.bankapp.models.Account;

public abstract class AccountDecorator extends Account {
    protected final Account wrapped;

    public AccountDecorator(Account wrapped) {
        super(wrapped.getType(), wrapped.getBalance());
        this.wrapped = wrapped;
    }

    @Override
    public double calculateInterest() {
        return wrapped.calculateInterest();
    }
}
