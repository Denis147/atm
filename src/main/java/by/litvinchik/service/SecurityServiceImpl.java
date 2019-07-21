package by.litvinchik.service;

import by.litvinchik.model.Account;
import by.litvinchik.storage.AtmStorage;

public class SecurityServiceImpl implements SecurityService {

    private AtmStorage atmStorage;

    public SecurityServiceImpl(AtmStorage atmStorage) {
        this.atmStorage = atmStorage;
    }

    @Override
    public Account getAccount(String accountNumber, String pinCode) {
        Account account = atmStorage.findAccountByNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account with number " + accountNumber + " not found");
        }
        if (!pinCode.equals(account.getPinCode())) {
            throw new IllegalArgumentException("Invalid pin code");
        }
        return account;
    }
}
