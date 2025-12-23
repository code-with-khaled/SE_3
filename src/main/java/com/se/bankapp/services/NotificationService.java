package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.observers.AccountObserver;
import com.se.bankapp.observers.EmailObserver;
import com.se.bankapp.observers.InAppObserver;
import com.se.bankapp.observers.SmsObserver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private List<AccountObserver> observers = new ArrayList<>();

    public NotificationService() {
        addObserver(new EmailObserver());
        addObserver(new SmsObserver());
        addObserver(new InAppObserver());
    }

    public void addObserver(AccountObserver o) {
        observers.add(o);
    }

    public void removeObserver(AccountObserver o) {
        observers.remove(o);
    }

    @Async
    public void notify(Account account, String action, double amount) {
        for (AccountObserver o : observers) {
            o.update(account, action, amount);
        }
        System.out.println("-----------------------------------------");
    }
}
