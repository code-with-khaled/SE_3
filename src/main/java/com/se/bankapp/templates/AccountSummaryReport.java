package com.se.bankapp.templates;

import com.se.bankapp.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountSummaryReport extends ReportGenerator {
    private final AccountRepository accountRepository;

    public AccountSummaryReport(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    protected List<String> fetchData() {
        return accountRepository.findAll()
                                .stream()
                                .map(acc -> "Account#" + acc.getId()
                                        + " type=" + acc.getType()
                                        + " state=" + acc.getState()
                                        + " balance=" + acc.getBalance()
                                        + " isPremium=" + acc.isPremium())
                                .toList();
    }

    @Override
    protected String formatData(List<String> data) {
        return String.join("\n", data);
    }

    @Override
    protected String export(String report) {
        return "Account Summery Report:\n----------------------\n" + report;
    }
}
