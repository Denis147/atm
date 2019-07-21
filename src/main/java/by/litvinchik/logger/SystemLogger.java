package by.litvinchik.logger;

public class SystemLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
