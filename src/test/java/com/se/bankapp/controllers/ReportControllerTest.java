package com.se.bankapp.controllers;

import com.se.bankapp.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportService service;

    @MockitoBean
    private org.springframework.cache.CacheManager cacheManager;

    @Test
    void getDailyReport_returnsString() throws Exception {
        when(service.getDailyReport()).thenReturn("Daily report content");

        mockMvc.perform(get("/reports/daily"))
                .andExpect(status().isOk())
                .andExpect(content().string("Daily report content"));
    }

    @Test
    void getSummaryReport_returnsString() throws Exception {
        when(service.getAccountSummaryReport()).thenReturn("Summary report content");

        mockMvc.perform(get("/reports/summary"))
                .andExpect(status().isOk())
                .andExpect(content().string("Summary report content"));
    }

    @Test
    void getAuditLog_returnsString() throws Exception {
        when(service.getAuditLog()).thenReturn("Audit log content");

        mockMvc.perform(get("/reports/audit"))
                .andExpect(status().isOk())
                .andExpect(content().string("Audit log content"));
    }
}
