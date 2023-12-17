package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        synchronized (accounts) {
            Optional<Account> fromAccount = getById(fromId);
            Optional<Account> toAccount = getById(toId);
            if (fromAccount.isPresent() && toAccount.isPresent() &&  fromAccount.get().amount() >= amount) {
                update(new Account(fromId, fromAccount.get().amount() - amount));
                update(new Account(toId, toAccount.get().amount() + amount));
                rsl = true;
            }
        }
        return rsl;
    }
}
