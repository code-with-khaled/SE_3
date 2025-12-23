package com.se.bankapp.controllers;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    void createAccount_returnsAccount() throws Exception {
        Account acc = new Account(1L, AccountType.SAVINGS, 100.0);

        when(accountService.create(AccountType.SAVINGS, 100.0)).thenReturn(acc);

        mockMvc.perform(post("/accounts")
                        .param("type", "SAVINGS")
                        .param("balance", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    @Test
    void getBalance_returnsDouble() throws Exception {
        when(accountService.getBalance(1L)).thenReturn(200.0);

        mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));
    }

    @Test
    void deposit_returnsUpdatedAccount() throws Exception {
        Account acc = new Account(1L, AccountType.SAVINGS, 100.0);
        acc.setBalance(150.0);

        when(accountService.deposit(1L, 50.0)).thenReturn(acc);

        mockMvc.perform(post("/accounts/1/deposit")
                        .param("amount", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(150.0));
    }

    @Test
    void withdraw_returnsUpdatedAccount() throws Exception {
        Account acc = new Account(1L, AccountType.SAVINGS, 200.0);
        acc.setBalance(150.0);

        when(accountService.withdraw(1L, 50.0)).thenReturn(acc);

        mockMvc.perform(post("/accounts/1/withdraw")
                        .param("amount", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(150.0));
    }

    @Test
    void transfer_returnsOk() throws Exception {
        doNothing().when(accountService).transfer(1L, 2L, 50.0);

        mockMvc.perform(post("/accounts/transfer")
                        .param("fromId", "1")
                        .param("toId", "2")
                        .param("amount", "50"))
                .andExpect(status().isOk());
    }
}
