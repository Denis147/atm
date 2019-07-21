package by.litvinchik.storage;

import by.litvinchik.configuration.AtmProperties;
import by.litvinchik.exception.UpdateAccountException;
import by.litvinchik.logger.Logger;
import by.litvinchik.model.Account;
import by.litvinchik.validator.AccountValidator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtmSimpleFileStorage implements AtmStorage {
    private String storagePath = AtmProperties.storagePath;
    private Logger logger = AtmProperties.logger;

    private Collection<Account> storedAccounts;
    private AccountConverter<String> accountConverter;

    private AtmSimpleFileStorage(){}

    private static AtmSimpleFileStorage instance;
    public static synchronized AtmStorage getInstance() {
        if (instance == null) {
            instance = new AtmSimpleFileStorage();
        }
        return instance;
    }

    @Override
    public Collection<Account> getAccounts(){
        if (storedAccounts == null) {
            storedAccounts = loadAccounts();
        }
        return storedAccounts;
    }

    @Override
    public void storeAccounts(){
        if (storedAccounts == null) {
            return;
        }
        storeAccounts(getAccounts());
    }

    @Override
    public Collection<Account> storeAccounts(Collection<Account> accounts){
        Collection<String> ignoredAccounts = new HashSet<>();
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(storagePath))) {
            for (Account account : accounts) {
                String errors = AccountValidator.validateAccount(account);
                boolean isValid = "".equals(errors);
                if (!isValid) {
                    ignoredAccounts.add(account.getNumber() + ": " + errors);
                } else {
                    writer.write(accountConverter.convertAccountToStorageData(account));
                }
            }
        } catch (IOException e) {
            logger.log("ERROR! Problem during storing data. " + e.getMessage());
        }
        if (!ignoredAccounts.isEmpty()) {
            logger.log("WARN! Next not valid accounts were ignored during storing");
            for (String ignoredAccount : ignoredAccounts) {
                logger.log(ignoredAccount);
            }
        }
        return accounts;
    }

    @Override
    public Account updateAccount(Account account) {
        String number = account.getNumber();
        if (number == null || "".equals(number)) {
            throw new IllegalArgumentException("Couldn't update account - number is empty");
        }
        Account storedAccount = findAccountByNumber(number);
        if (storedAccount == null) {
            throw new UpdateAccountException("Couldn't find account with number " + number);
        }
        storedAccount.setBalance(account.getBalance());
        storedAccount.setPinCode(account.getPinCode());
        return storedAccount;
    }

    @Override
    public Account findAccountByNumber(String number) {
        return getAccounts().stream()
                    .filter(account -> number.equals(account.getNumber()))
                    .findFirst()
                    .orElse(null);
    }

    private Collection<Account> loadAccounts(){
        Collection<String> ignoredAccounts = new HashSet<>();
        try (Stream<String> stream = Files.lines(Paths.get(storagePath))){
            List<Account> accounts = stream
                    .map(accountConverter::convertStorageDataToAccount)
                    .filter(Objects::nonNull)
                    .filter(account -> {
                        String errors = AccountValidator.validateAccount(account);
                        boolean isValid = "".equals(errors);
                        if (!isValid) {
                            ignoredAccounts.add(account.getNumber() + ": " + errors);
                        }
                        return isValid;
                    })
                    .collect(Collectors.toList());
            if (!ignoredAccounts.isEmpty()) {
                logger.log("WARN! Next not valid accounts were found");
                for (String ignoredAccount : ignoredAccounts) {
                    logger.log(ignoredAccount);
                }
            }
            return accounts;
        } catch (IOException e) {
            logger.log("ERROR! Problem during reading stored data. " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void setAccountConverter(AccountConverter<String> accountConverter) {
        this.accountConverter = accountConverter;
    }
}
