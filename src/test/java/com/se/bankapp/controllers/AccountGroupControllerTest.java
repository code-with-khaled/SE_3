package com.se.bankapp.controllers;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountGroup;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.services.AccountGroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountGroupController.class)
class AccountGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountGroupService groupService;

    @MockitoBean
    private org.springframework.cache.CacheManager cacheManager;

    @Test
    void createGroup_returnsGroup() throws Exception {
        AccountGroup group = new AccountGroup();
        group.setId(1L);
        group.setName("Family");

        when(groupService.createGroup(eq("Family"), anyList())).thenReturn(group);

        mockMvc.perform(post("/account-groups")
                        .param("name", "Family")
                        .contentType("application/json")
                        .content("[1,2]")) // JSON body with account IDs
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Family"));
    }

    @Test
    void getMembers_returnsAccounts() throws Exception {
        Account acc1 = new Account(AccountType.SAVINGS, 100.0);
        Account acc2 = new Account(AccountType.CHECKING, 200.0);
        acc1.setBalance(100.0);
        acc2.setBalance(200.0);

        when(groupService.getMembers(1L)).thenReturn(List.of(acc1, acc2));

        mockMvc.perform(get("/account-groups/1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(100.0))
                .andExpect(jsonPath("$[1].balance").value(200.0));
    }

    @Test
    void deposit_returnsUpdatedAccounts() throws Exception {
        Account acc1 = new Account(AccountType.SAVINGS, 150.0);
        Account acc2 = new Account(AccountType.CHECKING, 250.0);

        when(groupService.depositToGroup(1L, 100.0)).thenReturn(List.of(acc1, acc2));

        mockMvc.perform(post("/account-groups/1/deposit")
                        .param("amount", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(150.0))
                .andExpect(jsonPath("$[1].balance").value(250.0));
    }

    @Test
    void withdraw_returnsUpdatedAccounts() throws Exception {
        Account acc1 = new Account(AccountType.SAVINGS, 50.0);
        Account acc2 = new Account(AccountType.CHECKING, 150.0);

        when(groupService.withdrawFromGroup(1L, 100.0)).thenReturn(List.of(acc1, acc2));

        mockMvc.perform(post("/account-groups/1/withdraw")
                        .param("amount", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(50.0))
                .andExpect(jsonPath("$[1].balance").value(150.0));
    }
}
