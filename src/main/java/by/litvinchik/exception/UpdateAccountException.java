package by.litvinchik.exception;

public class UpdateAccountException extends RuntimeException {

    public UpdateAccountException() {
    }

    public UpdateAccountException(String message) {
        super(message);
    }
}
