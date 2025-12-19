package com.se.bankapp.decorators;

import com.se.bankapp.models.Account;

public class PremiumAccountDecorator extends AccountDecorator {
    public PremiumAccountDecorator(Account wrapped) { super(wrapped); }

    @Override
    public double calculateInterest() {
        return super.calculateInterest() * 1.2; // Premium accounts get 20% extra interest
    }
}
