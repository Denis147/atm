package by.litvinchik.validator;

import by.litvinchik.validator.AccountValidator;
import org.junit.Assert;
import org.junit.Test;

public class AccountNumberValidatorTest {

    @Test
    public void validNullAccountNumberTest() {
        boolean isValid = AccountValidator.isValidAccountNumber(null);
        Assert.assertFalse(isValid);
    }

    @Test
    public void validAccountNumberTest() {
        boolean isValid = AccountValidator.isValidAccountNumber("1123-1323-3123-3133");
        Assert.assertTrue(isValid);
    }

    @Test
    public void invalidAccountNumberTest() {
        boolean isValid = AccountValidator.isValidAccountNumber("11d3-1323-3123-3133");
        Assert.assertFalse(isValid);
    }

    @Test
    public void invalidAccountNumberTest2() {
        boolean isValid = AccountValidator.isValidAccountNumber("1143-13d3-3123-3133");
        Assert.assertFalse(isValid);
    }

    @Test
    public void invalidAccountNumberTest3() {
        boolean isValid = AccountValidator.isValidAccountNumber("11433-1343-3123-3133");
        Assert.assertFalse(isValid);
    }

    @Test
    public void invalidAccountNumberTest4() {
        boolean isValid = AccountValidator.isValidAccountNumber("1143-13434-3123-3133");
        Assert.assertFalse(isValid);
    }

    @Test
    public void longerAccountNumberTest() {
        boolean isValid = AccountValidator.isValidAccountNumber("1143-1343-3123-3133-3243");
        Assert.assertFalse(isValid);
    }
}
