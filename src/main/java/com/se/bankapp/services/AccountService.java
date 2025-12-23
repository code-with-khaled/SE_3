package com.se.bankapp.services;

import com.se.bankapp.approval.ApprovalChainBuilder;
import com.se.bankapp.approval.ApprovalHandler;
import com.se.bankapp.approval.TransactionApprovalRequest;
import com.se.bankapp.behaviors.state.AccountStateFactory;
import com.se.bankapp.behaviors.type.AccountTypeFactory;
import com.se.bankapp.decorators.PremiumAccountDecorator;
import com.se.bankapp.factories.AccountCreator;
import com.se.bankapp.factories.AccountFactory;
import com.se.bankapp.models.Account;
import com.se.bankapp.models.AccountType;
import com.se.bankapp.models.TransactionRecord;
import com.se.bankapp.repositories.AccountRepository;
import com.se.bankapp.repositories.TransactionRecordRepository;
import com.se.bankapp.behaviors.state.AccountStateBehavior;
import com.se.bankapp.behaviors.type.AccountTypeBehavior;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {
    private final AccountRepository repo;
    private final TransactionRecordRepository txRepo;
    private final NotificationService notificationService;

    public AccountService(AccountRepository repo, TransactionRecordRepository txRepo, NotificationService notificationService) {
        this.repo = repo;
        this.txRepo = txRepo;
        this.notificationService = notificationService;
    }

    @CacheEvict(value = "dailyReports", key = "T(java.time.LocalDate).now()")
    public Account create(AccountType type, double balance) {
        AccountCreator creator = AccountFactory.getCreator(type);
        Account acc = creator.create(balance);

        acc = repo.save(acc);

        txRepo.save(new TransactionRecord(acc.getId(), "CREATE", balance));

        notificationService.notify(acc, "CREATE", balance);
        return acc;
    }

    public double getBalance(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account not found"))
                .getBalance();
    }

    @Transactional
    @CacheEvict(value = "dailyReports", key = "T(java.time.LocalDate).now()")
    public Account deposit(long id, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        Account acc = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));

        // Approval workflow
        TransactionApprovalRequest request = new TransactionApprovalRequest(acc.getId(), amount, "DEPOSIT");
        ApprovalHandler chain = ApprovalChainBuilder.buildChain();
        chain.handle(request);

        // Execute if approved
        AccountStateBehavior stateBehavior = AccountStateFactory.getBehavior(acc.getState());
        stateBehavior.ensureAllowed(acc);

        AccountTypeBehavior typeBehavior = AccountTypeFactory.getBehavior(acc.getType());
        typeBehavior.deposit(acc, amount);

        txRepo.save(new TransactionRecord(acc.getId(), "DEPOSIT", amount));

        notificationService.notify(acc, "DEPOSIT", amount);
        return acc;
    }

    @Transactional
    @CacheEvict(value = "dailyReports", key = "T(java.time.LocalDate).now()")
    public Account withdraw(long id, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        Account acc = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));

        // Approval workflow
        TransactionApprovalRequest request = new TransactionApprovalRequest(acc.getId(), amount, "WITHDRAW");
        ApprovalHandler chain = ApprovalChainBuilder.buildChain();
        chain.handle(request);

        // Execute if approved
        AccountStateBehavior stateBehavior = AccountStateFactory.getBehavior(acc.getState());
        stateBehavior.ensureAllowed(acc);

        AccountTypeBehavior typeBehavior = AccountTypeFactory.getBehavior(acc.getType());
        typeBehavior.withdraw(acc, amount);

        txRepo.save(new TransactionRecord(acc.getId(), "WITHDRAW", amount));

        notificationService.notify(acc, "WITHDRAW", amount);
        return acc;
    }

    @Transactional
    public void transfer(long fromId, long toId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

        Account from = repo.findById(fromId).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account to = repo.findById(toId).orElseThrow(() -> new NoSuchElementException("Account not found"));

        // Approval workflow
        TransactionApprovalRequest request = new TransactionApprovalRequest(from.getId(), amount, "TRANSFER");
        ApprovalHandler chain = ApprovalChainBuilder.buildChain();
        chain.handle(request);

        // State checks
        AccountStateBehavior fromStateBehavior = AccountStateFactory.getBehavior(from.getState());
        AccountStateBehavior toStateBehavior = AccountStateFactory.getBehavior(to.getState());
        fromStateBehavior.ensureAllowed(from);
        toStateBehavior.ensureAllowed(to);

        AccountTypeBehavior fromTypeBehavior = AccountTypeFactory.getBehavior(from.getType());
        AccountTypeBehavior toTypeBehavior = AccountTypeFactory.getBehavior(to.getType());
        fromTypeBehavior.withdraw(from, amount);
        toTypeBehavior.deposit(to, amount);

        repo.save(from);
        repo.save(to);

        txRepo.save(new TransactionRecord(from.getId(), "TRANSFER_OUT", amount));
        txRepo.save(new TransactionRecord(to.getId(), "TRANSFER_IN", amount));

        notificationService.notify(from, "TRANSFER_OUT", amount);
        notificationService.notify(to, "TRANSFER_IN", amount);
    }


    @Transactional
    public Account freezeAccount(long id) {
        Account acc = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        AccountStateBehavior behavior = AccountStateFactory.getBehavior(acc.getState());
        behavior.freeze(acc);

        repo.save(acc);

        notificationService.notify(acc, "STATE_CHANGE", 0);
        return acc;
    }

    @Transactional
    public Account suspendAccount(long id) {
        Account acc = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        AccountStateBehavior behavior = AccountStateFactory.getBehavior(acc.getState());
        behavior.suspend(acc);

        repo.save(acc);

        notificationService.notify(acc, "STATE_CHANGE", 0);
        return acc;
    }

    @Transactional
    public Account activateAccount(long id) {
        Account acc = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        AccountStateBehavior behavior = AccountStateFactory.getBehavior(acc.getState());
        behavior.activate(acc);

        repo.save(acc);

        notificationService.notify(acc, "STATE_CHANGE", 0);
        return acc;
    }

    @Transactional
    public Account closeAccount(long id) {
        Account acc = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        AccountStateBehavior behavior = AccountStateFactory.getBehavior(acc.getState());
        behavior.close(acc);

        repo.save(acc);

        notificationService.notify(acc, "STATE_CHANGE", 0);
        return acc;
    }


    public double calculateInterest(long id) {
        Account acc = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
        if (acc.getInterestStrategy() == null) return 0;

        if(acc.isPremium()) {
            acc = new PremiumAccountDecorator(acc);
        }

        return acc.calculateInterest();
    }

    @Transactional
    public Account upgradeToPremium(long id) {
        Account acc = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        acc.setPremium(true);

        repo.save(acc);

        notificationService.notify(acc, "UPGRADED_TO_PREMIUM", 0);
        return acc;
    }

    public List<TransactionRecord> getHistory(long accountId) {
        return txRepo.findByAccountIdOrderByCreatedAtDesc(accountId);
    }
}

