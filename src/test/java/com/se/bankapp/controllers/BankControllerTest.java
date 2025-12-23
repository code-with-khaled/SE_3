package com.se.bankapp.controllers;

import com.se.bankapp.facade.BankingFacade;
import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankController.class)
class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankingFacade facade;

    @Test
    void createAccount_returnsAccount() throws Exception {
        Account acc = new Account(AccountType.SAVINGS, 100.0);
        when(facade.createAccount(AccountType.SAVINGS, 100.0)).thenReturn(acc);

        mockMvc.perform(post("/bank/accounts")
                        .param("type", "SAVINGS")
                        .param("balance", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    @Test
    void getDailyReport_returnsString() throws Exception {
        when(facade.getDailyReport()).thenReturn("Daily report content");

        mockMvc.perform(get("/bank/reports/daily"))
                .andExpect(status().isOk())
                .andExpect(content().string("Daily report content"));
    }
}
