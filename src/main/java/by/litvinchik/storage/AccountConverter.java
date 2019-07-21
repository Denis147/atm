package by.litvinchik.storage;

import by.litvinchik.model.Account;

interface AccountConverter<T> {

    Account convertStorageDataToAccount(T data);

    T convertAccountToStorageData(Account account);
}
