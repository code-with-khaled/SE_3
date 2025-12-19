package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountGroup;
import com.se.bankapp.repositories.AccountGroupRepository;
import com.se.bankapp.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountGroupService {

    private final AccountGroupRepository groupRepo;
    private final AccountRepository accountRepo;
    private final AccountService accountService;

    public AccountGroupService(AccountGroupRepository groupRepo,
                               AccountRepository accountRepo,
                               AccountService accountService) {
        this.groupRepo = groupRepo;
        this.accountRepo = accountRepo;
        this.accountService = accountService;
    }

    public AccountGroup createGroup(String name, List<Long> accountIds) {
        List<Account> accounts = accountRepo.findAllById(accountIds);
        AccountGroup group = new AccountGroup(name, accounts);
        return groupRepo.save(group);
    }

    public List<Account> getMembers(Long groupId) {
        return groupRepo.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"))
                .getMembers();
    }

    @Transactional
    public List<Account> depositToGroup(Long groupId, double amount) {
        AccountGroup group = groupRepo.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));
        double split = amount / group.getMembers().size();
        for (Account acc : group.getMembers()) {
            accountService.deposit(acc.getId(), split);
        }
        return group.getMembers();
    }

    @Transactional
    public List<Account> withdrawFromGroup(Long groupId, double amount) {
        AccountGroup group = groupRepo.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));
        double split = amount / group.getMembers().size();
        for (Account acc : group.getMembers()) {
            accountService.withdraw(acc.getId(), split);
        }
        return group.getMembers();
    }
}
