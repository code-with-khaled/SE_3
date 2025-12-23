package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountGroup;
import com.se.bankapp.models.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountGroupServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountGroupService accountGroupService;

    @Test
    void createGroup_and_getMembers() {
        // Create two accounts
        Account acc1 = accountService.create(AccountType.SAVINGS, 100.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 200.0);

        // Create group with those accounts
        AccountGroup group = accountGroupService.createGroup("Family Group",
                List.of(acc1.getId(), acc2.getId()));

        assertNotNull(group.getId());
        assertEquals("Family Group", group.getName());

        // Verify members
        List<Account> members = accountGroupService.getMembers(group.getId());
        assertEquals(2, members.size());
        assertTrue(members.stream().anyMatch(a -> a.getId().equals(acc1.getId())));
        assertTrue(members.stream().anyMatch(a -> a.getId().equals(acc2.getId())));
    }

    @Test
    void depositToGroup_splitsAmountAcrossMembers() {
        Account acc1 = accountService.create(AccountType.SAVINGS, 100.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 200.0);

        AccountGroup group = accountGroupService.createGroup("Deposit Group",
                List.of(acc1.getId(), acc2.getId()));

        // Deposit 100 total → split 50 each
        accountGroupService.depositToGroup(group.getId(), 100.0);

        double balance1 = accountService.getBalance(acc1.getId());
        double balance2 = accountService.getBalance(acc2.getId());

        assertEquals(150.0, balance1, 0.0001);
        assertEquals(250.0, balance2, 0.0001);
    }

    @Test
    void withdrawFromGroup_splitsAmountAcrossMembers() {
        Account acc1 = accountService.create(AccountType.SAVINGS, 300.0);
        Account acc2 = accountService.create(AccountType.CHECKING, 300.0);

        AccountGroup group = accountGroupService.createGroup("Withdraw Group",
                List.of(acc1.getId(), acc2.getId()));

        // Withdraw 100 total → split 50 each
        accountGroupService.withdrawFromGroup(group.getId(), 100.0);

        double balance1 = accountService.getBalance(acc1.getId());
        double balance2 = accountService.getBalance(acc2.getId());

        assertEquals(250.0, balance1, 0.0001);
        assertEquals(250.0, balance2, 0.0001);
    }

    @Test
    void getMembers_invalidGroup_throwsException() {
        assertThrows(NoSuchElementException.class,
                () -> accountGroupService.getMembers(999L));
    }
}
