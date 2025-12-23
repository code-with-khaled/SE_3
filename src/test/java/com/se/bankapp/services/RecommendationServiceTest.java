package com.se.bankapp.services;

import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.models.TransactionRecord;
import com.se.bankapp.repositories.TransactionRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecommendationServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private TransactionRecordRepository txRepo;

    @Test
    void recommendation_savingMoreThanSpending() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);
        txRepo.save(new TransactionRecord(acc.getId(), "DEPOSIT", 200.0));
        txRepo.save(new TransactionRecord(acc.getId(), "WITHDRAW", 100.0));

        String rec = recommendationService.generateRecommendation(acc.getId());
        assertTrue(rec.contains("save more than you spend"));
    }

    @Test
    void recommendation_spendingMoreThanDeposits() {
        Account acc = accountService.create(AccountType.CHECKING, 100.0);
        txRepo.save(new TransactionRecord(acc.getId(), "DEPOSIT", 50.0));
        txRepo.save(new TransactionRecord(acc.getId(), "WITHDRAW", 100.0));

        String rec = recommendationService.generateRecommendation(acc.getId());
        assertTrue(rec.contains("spending exceeds deposits"));
    }

    @Test
    void recommendation_balancedHabits() {
        Account acc = accountService.create(AccountType.SAVINGS, 100.0);
        txRepo.save(new TransactionRecord(acc.getId(), "DEPOSIT", 100.0));
        txRepo.save(new TransactionRecord(acc.getId(), "WITHDRAW", 90.0));

        String rec = recommendationService.generateRecommendation(acc.getId());
        assertTrue(rec.contains("Keep up your current banking habits"));
    }
}
