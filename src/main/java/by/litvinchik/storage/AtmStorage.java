package by.litvinchik.storage;

import by.litvinchik.model.Account;

import java.util.Collection;

public interface AtmStorage {

    Collection<Account> getAccounts();

    void storeAccounts();

    Collection<Account> storeAccounts(Collection<Account> accounts);

    Account updateAccount(Account account);

    Account findAccountByNumber(String number);
}
