package com.se.bankapp.controllers;

import com.se.bankapp.models.ScheduledTransaction;
import com.se.bankapp.services.ScheduledTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduledTransactionController.class)
class ScheduledTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScheduledTransactionService service;

    @Test
    void scheduleTransaction_returnsScheduledTransaction() throws Exception {
        ScheduledTransaction st = new ScheduledTransaction(1L);
        st.setAccountId(10L);
        st.setAmount(100.0);
        st.setType("DEPOSIT");
        st.setScheduledAt(Instant.now());

        when(service.scheduleTransaction(any(ScheduledTransaction.class))).thenReturn(st);

        mockMvc.perform(post("/scheduled")
                        .contentType("application/json")
                        .content("{\"accountId\":10,\"amount\":100.0,\"type\":\"DEPOSIT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountId").value(10))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.type").value("DEPOSIT"));
    }

    @Test
    void cancelTransaction_returnsOk() throws Exception {
        doNothing().when(service).cancelScheduledTransaction(1L);

        mockMvc.perform(put("/scheduled/1/cancel"))
                .andExpect(status().isOk());
    }
}
