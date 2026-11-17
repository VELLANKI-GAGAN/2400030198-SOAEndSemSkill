package com.bank.account.service;

import com.bank.account.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccountService {

    private final Map<Long, Account> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1000);

    public AccountService() {
        create(new Account(null, "Asha Rao", "SAVINGS", new BigDecimal("25000.00")));
        create(new Account(null, "Ravi Kumar", "CURRENT", new BigDecimal("78000.50")));
    }

    public List<Account> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Account create(Account in) {
        long id = sequence.incrementAndGet();
        Account saved = new Account(id, in.holderName(), in.type(), in.balance());
        store.put(id, saved);
        return saved;
    }
}
