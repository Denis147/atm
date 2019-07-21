package by.litvinchik.validator;

import by.litvinchik.configuration.AtmProperties;
import by.litvinchik.model.Account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {

    public static String validateAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Couldn't valid empty account");
        }
        StringBuilder errorMessage = new StringBuilder();
        if (!isValidAccountNumber(account.getNumber())) {
            errorMessage.append("Not valid account number: ").append(account.getNumber()).append(";");
        }
        if (account.getBalance() == null) {
            errorMessage.append("Balance is not initialized;");
        }
        if (account.getPinCode() == null) {
            errorMessage.append("Pin code is not initialized;");
        }
        return errorMessage.toString();
    }

    public static boolean isValidAccountNumber(String number){
        if (number == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(AtmProperties.accountNumberRegexp);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
