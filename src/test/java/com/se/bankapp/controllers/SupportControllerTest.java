package com.se.bankapp.controllers;

import com.se.bankapp.models.SupportTicket;
import com.se.bankapp.services.SupportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupportController.class)
class SupportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SupportService service;

    @MockitoBean
    private org.springframework.cache.CacheManager cacheManager;

    @Test
    void createTicket_returnsTicket() throws Exception {
        SupportTicket ticket = new SupportTicket();
        ticket.setId(1L);
        ticket.setAccountId(10L);
        ticket.setSubject("Login Issue");
        ticket.setDescription("Cannot log in to account");
        ticket.setCreatedAt(LocalDateTime.now());

        when(service.createTicket(10L, "Login Issue", "Cannot log in to account")).thenReturn(ticket);

        mockMvc.perform(post("/support/tickets")
                        .param("accountId", "10")
                        .param("subject", "Login Issue")
                        .param("description", "Cannot log in to account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountId").value(10))
                .andExpect(jsonPath("$.subject").value("Login Issue"))
                .andExpect(jsonPath("$.description").value("Cannot log in to account"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void getTickets_returnsList() throws Exception {
        SupportTicket t1 = new SupportTicket();
        t1.setId(1L);
        t1.setAccountId(10L);
        t1.setSubject("Login Issue");
        t1.setDescription("Cannot log in");

        SupportTicket t2 = new SupportTicket();
        t2.setId(2L);
        t2.setAccountId(10L);
        t2.setSubject("Payment Issue");
        t2.setDescription("Payment not processed");

        when(service.getTicketsByAccount(10L)).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/support/tickets/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subject").value("Login Issue"))
                .andExpect(jsonPath("$[0].status").value("OPEN"))
                .andExpect(jsonPath("$[1].subject").value("Payment Issue"))
                .andExpect(jsonPath("$[1].status").value("OPEN"));
    }

    @Test
    void advanceTicket_returnsUpdatedTicket() throws Exception {
        SupportTicket ticket = new SupportTicket();
        ticket.setId(1L);
        ticket.setAccountId(10L);
        ticket.setSubject("Login Issue");
        ticket.setDescription("Cannot log in");
        ticket.nextState(); // moves from OPEN → IN_PROGRESS

        when(service.advanceTicket(1L)).thenReturn(ticket);

        mockMvc.perform(post("/support/tickets/1/advance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }
}
