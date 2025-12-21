package com.se.bankapp.behaviors.type;

import com.se.bankapp.models.AccountType;

public class AccountTypeFactory {
    public static AccountTypeBehavior getBehavior(AccountType type) {
        return switch (type) {
            case CHECKING -> new CheckingBehavior();
            case SAVINGS -> new SavingsBehavior();
            case LOAN -> new LoanBehavior();
            case INVESTMENT -> new InvestmentBehavior();
        };
    }
}
