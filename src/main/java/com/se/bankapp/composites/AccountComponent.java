package com.se.bankapp.composites;

public interface AccountComponent {
    double getBalance();
    void deposit(double amount);
    void withdraw(double amount);
}
