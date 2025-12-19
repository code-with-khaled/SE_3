package com.se.bankapp.controllers;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountGroup;
import com.se.bankapp.services.AccountGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-groups")
public class AccountGroupController {

    private final AccountGroupService groupService;

    public AccountGroupController(AccountGroupService groupService) {
        this.groupService = groupService;
    }

    // Create group
    @PostMapping
    public AccountGroup createGroup(@RequestParam String name, @RequestBody List<Long> accountIds) {
        return groupService.createGroup(name, accountIds);
    }

    // Get group members
    @GetMapping("/{groupId}/members")
    public List<Account> getMembers(@PathVariable Long groupId) {
        return groupService.getMembers(groupId);
    }

    // Deposit to group
    @PostMapping("/{groupId}/deposit")
    public List<Account> deposit(@PathVariable Long groupId, @RequestParam double amount) {
        return groupService.depositToGroup(groupId, amount);
    }

    // Withdraw from group
    @PostMapping("/{groupId}/withdraw")
    public List<Account> withdraw(@PathVariable Long groupId, @RequestParam double amount) {
        return groupService.withdrawFromGroup(groupId, amount);
    }
}
