package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.models.SupportTicket;
import com.se.bankapp.models.TicketState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SupportServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SupportService supportService;

    @Test
    void createTicket_and_retrieveByAccount() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);

        SupportTicket ticket = supportService.createTicket(
                acc.getId(),
                "Login Issue",
                "Cannot access online banking"
        );

        assertNotNull(ticket.getId());
        assertEquals("Login Issue", ticket.getSubject());
        assertEquals("Cannot access online banking", ticket.getDescription());

        List<SupportTicket> tickets = supportService.getTicketsByAccount(acc.getId());
        assertEquals(1, tickets.size());
        assertEquals(ticket.getId(), tickets.getFirst().getId());
    }

    @Test
    void advanceTicket_changesState_and_notifies() {
        Account acc = accountService.create(AccountType.CHECKING, 200.0);

        SupportTicket ticket = supportService.createTicket(
                acc.getId(),
                "Payment Error",
                "Transaction failed"
        );

        TicketState initialStatus = ticket.getStatus();

        SupportTicket advanced = supportService.advanceTicket(ticket.getId());

        assertNotEquals(initialStatus, advanced.getStatus());
        assertEquals(ticket.getId(), advanced.getId());
    }

    @Test
    void getTicketsByAccount_returnsEmptyList_whenNoTickets() {
        Account acc = accountService.create(AccountType.SAVINGS, 150.0);

        List<SupportTicket> tickets = supportService.getTicketsByAccount(acc.getId());
        assertTrue(tickets.isEmpty());
    }

    @Test
    void advanceTicket_invalidId_throwsException() {
        assertThrows(NoSuchElementException.class,
                () -> supportService.advanceTicket(999L));
    }

    @Test
    void createTicket_invalidAccount_throwsException() {
        assertThrows(NoSuchElementException.class,
                () -> supportService.createTicket(999L, "Subject", "Description"));
    }
}
