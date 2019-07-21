package by.litvinchik.service;

import by.litvinchik.model.Account;

import java.math.BigDecimal;
import java.util.Collection;

public interface AtmService {
    Collection<Account> getAll();

    void save(Collection<Account> accounts);

    void geCache(Account account, BigDecimal cache);

    void replenishmentAccount(Account account, BigDecimal cache);
}
