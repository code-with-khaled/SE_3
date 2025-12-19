package com.se.bankapp.factories;

import com.se.bankapp.models.AccountType;

public class AccountFactory {
    public static AccountCreator getCreator(AccountType type) {
        return switch (type) {
            case SAVINGS -> new SavingsAccountCreator();
            case CHECKING -> new CheckingAccountCreator();
            case LOAN -> new LoanAccountCreator();
            case INVESTMENT ->  new InvestmentAccountCreator();
        };
    }
}
