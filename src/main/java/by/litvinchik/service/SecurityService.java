package by.litvinchik.service;

import by.litvinchik.model.Account;

public interface SecurityService {
    Account getAccount(String accountNumber, String pinCode);
}
