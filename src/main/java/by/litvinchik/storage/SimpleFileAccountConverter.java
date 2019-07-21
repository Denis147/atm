package by.litvinchik.storage;

import by.litvinchik.configuration.AtmProperties;
import by.litvinchik.logger.Logger;
import by.litvinchik.model.Account;

import java.math.BigDecimal;

public class SimpleFileAccountConverter implements AccountConverter<String> {
    private static final String DATA_SEPARATOR = " ";

    private Logger logger = AtmProperties.logger;

    public Account convertStorageDataToAccount(String data) {
        if (data == null) {
            return null;
        }
        String[] accountData = data.split(DATA_SEPARATOR);
        if (accountData.length < 3) {
            logger.log("Not valid account data: " + data);
            return null;
        }
        BigDecimal balance = null;
        try {
            balance = new BigDecimal(accountData[2]);
        } catch (NumberFormatException e) {
            logger.log("Not valid account data (incorrect balance format) : " + data);
        }
        return Account.builder()
                .setNumber(accountData[0])
                .setPinCode(accountData[1])
                .setBalance(balance)
                .build();
    }

    public String convertAccountToStorageData(Account account) {
        return String.join(DATA_SEPARATOR, account.getNumber(), account.getPinCode(), account.getBalance().toString());
    }
}
