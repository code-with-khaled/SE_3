package com.se.bankapp.repositories;

import com.se.bankapp.models.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findByAccountId(Long accountId);
}

