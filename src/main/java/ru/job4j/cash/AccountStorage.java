package ru.job4j.cash;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class AccountStorage {
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public void delete(int id) {
        accounts.remove(id);
    }

    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);
        if (fromAccount != null && toAccount != null && fromAccount.amount() - amount >= 0) {
            accounts.put(fromId, new Account(fromId, fromAccount.amount() - amount));
            accounts.put(toId, new Account(toId, toAccount.amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}

