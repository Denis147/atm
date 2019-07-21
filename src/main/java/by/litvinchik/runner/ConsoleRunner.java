package by.litvinchik.runner;

import by.litvinchik.model.Account;
import by.litvinchik.service.AtmService;
import by.litvinchik.service.AtmServiceImpl;
import by.litvinchik.service.SecurityService;
import by.litvinchik.service.SecurityServiceImpl;
import by.litvinchik.storage.AtmSimpleFileStorage;
import by.litvinchik.storage.AtmStorage;
import by.litvinchik.storage.SimpleFileAccountConverter;
import by.litvinchik.validator.AccountValidator;

import java.io.Console;
import java.math.BigDecimal;

public class ConsoleRunner {

    private static AtmService atmService;
    private static SecurityService securityService;
    private static AtmStorage storage;

    static {
        storage = AtmSimpleFileStorage.getInstance();
        ((AtmSimpleFileStorage)storage).setAccountConverter(new SimpleFileAccountConverter());
        atmService = new AtmServiceImpl(storage);
        securityService = new SecurityServiceImpl(storage);
    }

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console is not supported");
            System.exit(1);
        }
        addShutdownHook();

        String userInput;
        Account account = null;
        while (true) {
            while (account == null) {
                System.out.println("Not authorized user. Please input account number");
                String accountNumber = console.readLine();
                boolean isValidNumber = AccountValidator.isValidAccountNumber(accountNumber);
                if (!isValidNumber) {
                    System.out.println("Not valid account number. Please try again");
                    continue;
                }
                System.out.println("Please input account pin");
                String pin = new String(console.readPassword());
                try {
                    account = securityService.getAccount(accountNumber, pin);
                } catch (Exception e) {
                    System.out.println("Couldn't authorized. " + e.getMessage());
                }
            }

            System.out.println();
            System.out.println();
            System.out.println("*****Available Options*****");
            System.out.println("*. Press 1 for checking balance");
            System.out.println("*. Press 2 for getting cache");
            System.out.println("*. Press 3 for account replenishment");
            System.out.println("*. Press 4 for choosing another account");
            System.out.println("*. Press 5 for exit");
            userInput = console.readLine("Input action number from the list: ");
            switch (userInput) {
                case "1":
                    System.out.println("Current balance: " + account.getBalance());
                    break;
                case "2":
                    String cache = console.readLine("Input cache value: ");
                    BigDecimal cacheValue;
                    try {
                        cacheValue = new BigDecimal(cache);
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect cache value format: " + cache);
                        break;
                    }
                    try {
                        atmService.geCache(account, cacheValue);
                    } catch (Exception e) {
                        System.out.println("Couldn't get cache. " + e.getMessage());
                        break;
                    }
                    System.out.println("Get cache: " + cache);
                    break;
                case "3":
                    String replenishment = console.readLine("Input replenishment value: ");
                    BigDecimal replenishmentValue;
                    try {
                        replenishmentValue = new BigDecimal(replenishment);
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect cache value format: " + replenishment);
                        break;
                    }
                    try {
                        atmService.replenishmentAccount(account, replenishmentValue);
                    } catch (Exception e) {
                        System.out.println("Couldn't replenishment account. " + e.getMessage());
                        break;
                    }
                    System.out.println("Replenishment account: " + replenishment);
                    break;
                case "4":
                    System.out.println("Logouting...");
                    account = null;
                    break;
                case "5":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Read the options carefully...");
            }
        }
    }

    private static void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Storing data...");
                storage.storeAccounts();
                System.out.println("Data was successfully stored.");
        }));
    }
}
