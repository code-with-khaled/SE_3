package com.se.bankapp.models;

import com.se.bankapp.strategies.InterestStrategy;
import com.se.bankapp.strategies.InvestmentInterest;
import com.se.bankapp.strategies.LoanInterest;
import com.se.bankapp.strategies.SavingsInterest;
import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // store enum as text in DB
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountState state = AccountState.ACTIVE;

    @Column(nullable = false)
    private double balance;

    @Transient
    private InterestStrategy interestStrategy;

    @Column(nullable = false)
    private boolean premium = false;


    public Account() {}
    public Account(AccountType type, double balance) {
        this.type = type;
        this.balance = balance;
    }

    @PostLoad
    private void attachStrategy() {
        switch (this.type) {
            case SAVINGS -> this.interestStrategy = new SavingsInterest();
            case LOAN -> this.interestStrategy = new LoanInterest();
            case INVESTMENT -> this.interestStrategy = new InvestmentInterest();
            case CHECKING -> this.interestStrategy = null;
        }
    }

    public long getId() {
        return id;
    }

    public AccountType getType() {
        return type;
    }

    public AccountState getState() {
        return state;
    }

    public double getBalance() {
        return balance;
    }

    public InterestStrategy getInterestStrategy() {
        return interestStrategy;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setInterestStrategy(InterestStrategy interestStrategy) {
        this.interestStrategy = interestStrategy;
    }

    public double calculateInterest() {
        if (interestStrategy == null) return 0;
        return interestStrategy.calculateInterest(this);
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
