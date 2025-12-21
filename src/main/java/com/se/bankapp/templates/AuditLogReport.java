package com.se.bankapp.templates;

import com.se.bankapp.repositories.TransactionRecordRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuditLogReport extends ReportGenerator {
    private final TransactionRecordRepository transactionRecordRepository;

    public AuditLogReport(TransactionRecordRepository transactionRecordRepository) {
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Override
    protected List<String> fetchData() {
        return transactionRecordRepository.findAll()
                                        .stream()
                                        .map(tr -> tr.getCreatedAt().toString()
                                                + " | acc=" + tr.getAccountId()
                                                + " | type=" + tr.getType()
                                                + " | amount=" + tr.getAmount())
                                        .toList();
    }

    @Override
    protected String formatData(List<String> data) {
        return String.join("\n", data);
    }

    @Override
    protected String export(String report) {
        return "Audit Log Report:\n----------------------\n" + report;
    }
}
