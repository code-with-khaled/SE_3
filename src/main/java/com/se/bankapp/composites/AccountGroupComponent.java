package com.se.bankapp.composites;

import com.se.bankapp.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountGroupComponent implements AccountComponent {
    private final List<AccountComponent> children = new ArrayList<>();

    public void add(AccountComponent component){
        children.add(component);
    }

    public void remove(AccountComponent component){
        children.remove(component);
    }

    @Override
    public double getBalance() {
        return children.stream().mapToDouble(AccountComponent::getBalance).sum();
    }

    @Override
    public void deposit(double amount) {
        if (children.isEmpty()) return;
        double share = amount / children.size();
        children.forEach(child -> child.deposit(share));
    }

    @Override
    public void withdraw(double amount) {
        if (children.isEmpty()) return;
        double share = amount / children.size();
        children.forEach(child -> child.withdraw(share));
    }

    public List<AccountComponent> getChildren() {
        return children;
    }

    public List<Account> getMemberAccounts() {
        return children.stream()
                .filter(c -> c instanceof SingleAccountComponent)
                .map(c -> ((SingleAccountComponent)c).getAccount())
                .toList();
    }
}
