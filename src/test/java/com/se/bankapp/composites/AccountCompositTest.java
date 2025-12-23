package com.se.bankapp.composites;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountCompositeTest {

    @Autowired
    private AccountService accountService;

    @Test
    void groupBalance_sumsChildrenBalances() {
        Account acc1 = accountService.create(AccountType.SAVINGS, 100.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 200.0);

        SingleAccountComponent child1 = new SingleAccountComponent(acc1, accountService);
        SingleAccountComponent child2 = new SingleAccountComponent(acc2, accountService);

        AccountGroupComponent group = new AccountGroupComponent();
        group.add(child1);
        group.add(child2);

        double totalBalance = group.getBalance();
        assertEquals(300.0, totalBalance, 0.0001);
    }

    @Test
    void deposit_splitsAmountAcrossChildren() {
        Account acc1 = accountService.create(AccountType.SAVINGS, 100.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 200.0);

        SingleAccountComponent child1 = new SingleAccountComponent(acc1, accountService);
        SingleAccountComponent child2 = new SingleAccountComponent(acc2, accountService);

        AccountGroupComponent group = new AccountGroupComponent();
        group.add(child1);
        group.add(child2);

        group.deposit(100.0); // split 50 each

        assertEquals(150.0, accountService.getBalance(acc1.getId()), 0.0001);
        assertEquals(250.0, accountService.getBalance(acc2.getId()), 0.0001);
    }

    @Test
    void withdraw_splitsAmountAcrossChildren() {
        Account acc1 = accountService.create(AccountType.SAVINGS, 300.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 300.0);

        SingleAccountComponent child1 = new SingleAccountComponent(acc1, accountService);
        SingleAccountComponent child2 = new SingleAccountComponent(acc2, accountService);

        AccountGroupComponent group = new AccountGroupComponent();
        group.add(child1);
        group.add(child2);

        group.withdraw(100.0); // split 50 each

        assertEquals(250.0, accountService.getBalance(acc1.getId()), 0.0001);
        assertEquals(250.0, accountService.getBalance(acc2.getId()), 0.0001);
    }

    @Test
    void getMemberAccounts_returnsUnderlyingAccounts() {
        Account acc1 = accountService.create(AccountType.SAVINGS, 100.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 200.0);

        SingleAccountComponent child1 = new SingleAccountComponent(acc1, accountService);
        SingleAccountComponent child2 = new SingleAccountComponent(acc2, accountService);

        AccountGroupComponent group = new AccountGroupComponent();
        group.add(child1);
        group.add(child2);

        var members = group.getMemberAccounts();
        assertEquals(2, members.size());
        assertTrue(members.stream().anyMatch(a -> a.getId().equals(acc1.getId())));
        assertTrue(members.stream().anyMatch(a -> a.getId().equals(acc2.getId())));
    }

    @Test
    void removeChild_excludesFromBalance() {
        Account acc1 = accountService.create(AccountType.SAVINGS, 100.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 200.0);

        SingleAccountComponent child1 = new SingleAccountComponent(acc1, accountService);
        SingleAccountComponent child2 = new SingleAccountComponent(acc2, accountService);

        AccountGroupComponent group = new AccountGroupComponent();
        group.add(child1);
        group.add(child2);

        group.remove(child1);

        double totalBalance = group.getBalance();
        assertEquals(200.0, totalBalance, 0.0001);
    }
}
