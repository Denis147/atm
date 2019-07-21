package by.litvinchik.service;

import by.litvinchik.configuration.AtmProperties;
import by.litvinchik.model.Account;
import by.litvinchik.storage.AtmStorage;

import java.math.BigDecimal;
import java.util.Collection;

public class AtmServiceImpl implements AtmService {

    private AtmStorage atmStorage;

    public AtmServiceImpl(AtmStorage atmStorage) {
        this.atmStorage = atmStorage;
    }

    @Override
    public Collection<Account> getAll() {
        return atmStorage.getAccounts();
    }

    @Override
    public void save(Collection<Account> accounts) {
        atmStorage.storeAccounts(accounts);
    }

    @Override
    public void geCache(Account account, BigDecimal cache) {
        if (cache.compareTo(account.getBalance()) > 0) {
            throw new IllegalArgumentException("Not enough money on the account for the operation");
        }
        if (cache.compareTo(AtmProperties.cacheLimit) > 0){
            throw new IllegalArgumentException("Cache withdrawal limit exceeded. Limit is " + AtmProperties.cacheLimit);
        }
        account.setBalance(account.getBalance().subtract(cache));
        atmStorage.updateAccount(account);
    }

    @Override
    public void replenishmentAccount(Account account, BigDecimal cache) {
        if (cache.compareTo(AtmProperties.replenishmentLimit) > 0){
            throw new IllegalArgumentException("Replenishment limit exceeded. Limit is " + AtmProperties.replenishmentLimit);
        }
        account.setBalance(account.getBalance().add(cache));
        atmStorage.updateAccount(account);
    }
}
