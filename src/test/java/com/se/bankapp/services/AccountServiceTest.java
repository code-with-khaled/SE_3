package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountState;
import com.se.bankapp.models.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import com.se.bankapp.models.TransactionRecord;
import java.util.List;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void create_deposit_history_end_to_end() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);
        accountService.deposit(acc.getId(), 25.0);

        double balance = accountService.getBalance(acc.getId());
        assertEquals(125.0, balance, 0.0001);

        List<TransactionRecord> history = accountService.getHistory(acc.getId());
        assertFalse(history.isEmpty());
        assertTrue(history.stream().anyMatch(tx -> tx.getType().equals("DEPOSIT")));
    }

    @Test
    void withdraw_end_to_end() {
        Account acc = accountService.create(AccountType.CHECKING, 200.0);
        accountService.withdraw(acc.getId(), 40.0);

        double balance = accountService.getBalance(acc.getId());
        assertEquals(160.0, balance, 0.0001);

        List<TransactionRecord> history = accountService.getHistory(acc.getId());
        assertTrue(history.stream().anyMatch(tx -> tx.getType().equals("WITHDRAW")));
    }

    @Test
    void transfer_end_to_end() {
        Account from = accountService.create(AccountType.CHECKING, 300.0);
        Account to   = accountService.create(AccountType.SAVINGS, 100.0);

        accountService.transfer(from.getId(), to.getId(), 50.0);

        assertEquals(250.0, accountService.getBalance(from.getId()), 0.0001);
        assertEquals(150.0, accountService.getBalance(to.getId()), 0.0001);

        List<TransactionRecord> fromHistory = accountService.getHistory(from.getId());
        List<TransactionRecord> toHistory   = accountService.getHistory(to.getId());

        assertTrue(fromHistory.stream().anyMatch(tx -> tx.getType().equals("TRANSFER_OUT")));
        assertTrue(toHistory.stream().anyMatch(tx -> tx.getType().equals("TRANSFER_IN")));
    }

    @Test
    void upgradeToPremium_and_calculateInterest() {
        Account acc = accountService.create(AccountType.SAVINGS, 1000.0);
        accountService.upgradeToPremium(acc.getId());

        Account upgraded = accountService.activateAccount(acc.getId()); // ensure active state
        assertTrue(upgraded.isPremium());

        double interest = accountService.calculateInterest(acc.getId());
        assertTrue(interest >= 0.0);
    }

    @Test
    void stateTransitions_workCorrectly() {
        Account acc = accountService.create(AccountType.SAVINGS, 500.0);

        Account frozen = accountService.freezeAccount(acc.getId());
        assertEquals(AccountState.FROZEN, frozen.getState());

        Account suspended = accountService.suspendAccount(acc.getId());
        assertEquals(AccountState.SUSPENDED, suspended.getState());

        Account active = accountService.activateAccount(acc.getId());
        assertEquals(AccountState.ACTIVE, active.getState());

        Account closed = accountService.closeAccount(acc.getId());
        assertEquals(AccountState.CLOSED, closed.getState());
    }

    @Test
    void deposit_invalidAmount_throwsException() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);
        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(acc.getId(), 0.0));
        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(acc.getId(), -10.0));
    }

    @Test
    void withdraw_invalidAmount_throwsException() {
        Account acc = accountService.create(AccountType.CHECKING, 100.0);
        assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(acc.getId(), 0.0));
        assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(acc.getId(), -20.0));
    }
}

