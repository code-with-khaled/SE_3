package com.se.bankapp.templates;

import com.se.bankapp.repositories.TransactionRecordRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class DailyTransactionReport extends ReportGenerator {
    private final TransactionRecordRepository transactionRecordRepository;
    private final ZoneId zone = ZoneId.systemDefault();

    public DailyTransactionReport(TransactionRecordRepository transactionRepository) {
        this.transactionRecordRepository = transactionRepository;
    }

    @Override
    protected List<String> fetchData() {
        LocalDate today = LocalDate.now(zone);
        Instant start = today.atStartOfDay(zone).toInstant();
        Instant end = today.plusDays(1).atStartOfDay(zone).toInstant();

        return transactionRecordRepository.findByCreatedAtBetween(start, end)
                                    .stream()
                                    .map(tr -> "Tx#" + tr.getId()
                                            + " acc=" + tr.getAccountId()
                                            + " type=" + tr.getType()
                                            + " amount=" + tr.getAmount()
                                            + " at=" + tr.getCreatedAt())
                                    .toList();
    }

    @Override
    protected String formatData(List<String> data) {
        return String.join("\n", data);
    }

    @Override
    protected String export(String report) {
        return "Daily Transactions Report:\n----------------------\n" + report;
    }
}
