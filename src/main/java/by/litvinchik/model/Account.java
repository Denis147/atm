package by.litvinchik.model;

import java.math.BigDecimal;

public class Account {
    private String number;
    private String pinCode;
    private BigDecimal balance;

    public String getNumber() {
        return number;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static Builder builder() {
        return new Account().new Builder();
    }


    public class Builder{
        private Builder() {

        }

        public Builder setNumber(String number) {
            Account.this.number = number;
            return this;
        }

        public Builder setPinCode(String pinCode) {
            Account.this.pinCode = pinCode;
            return this;
        }

        public Builder setBalance(BigDecimal balance) {
            Account.this.balance = balance;
            return this;
        }

        public Account build(){
            return Account.this;
        }

    }
}
