package com.se.bankapp.services;

import com.se.bankapp.models.TransactionRecord;
import com.se.bankapp.repositories.TransactionRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {
    private final TransactionRecordRepository transactionRecordRepository;

    public RecommendationService(TransactionRecordRepository transactionRecordRepository) {
        this.transactionRecordRepository = transactionRecordRepository;
    }

    public String generateRecommendation(long accountId) {
        List<TransactionRecord> txs = transactionRecordRepository.findByAccountId(accountId);

        double totalDeposits = txs.stream()
                .filter(tx -> tx.getType().equals("DEPOSIT"))
                .mapToDouble(TransactionRecord::getAmount)
                .sum();
        System.out.println("\n" + totalDeposits);
        double totalWithdraws = txs.stream()
                .filter(tx -> tx.getType().equals("WITHDRAW"))
                .mapToDouble(TransactionRecord::getAmount)
                .sum();
        System.out.println(totalWithdraws);

        if (totalDeposits > totalWithdraws * 1.2) {
            return "You save more than you spend - consider moving funds into a savings account";
        } else if (totalWithdraws > totalDeposits) {
            return "Your spending exceeds deposits — overdraft protection may help.";
        } else {
            return "Keep up your current banking habits.";
        }
    }
}
