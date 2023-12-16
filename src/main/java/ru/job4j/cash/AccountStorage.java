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
            accounts.put(account.id(), account);
        }
        return true;
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            Account prevAccount = accounts.get(account.id());
            return accounts.replace(account.id(), prevAccount, account);
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
            if (getById(fromId).isPresent() && getById(toId).isPresent() && accounts.get(fromId).amount() >= amount) {
                accounts.computeIfPresent(fromId, (k, v) -> new Account(k, v.amount() - amount));
                accounts.computeIfPresent(toId, (k, v) -> new Account(k, v.amount() + amount));
                rsl = true;
            }
        }
        return rsl;
    }
}
