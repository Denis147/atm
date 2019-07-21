package by.litvinchik.configuration;

import by.litvinchik.logger.Logger;
import by.litvinchik.logger.SystemLogger;

import java.math.BigDecimal;

public class AtmProperties {

    public static final Logger logger = new SystemLogger();
    public static final String storagePath = "D:\\storage.txt";
    public static final String accountNumberRegexp = "\\d{4}(-\\d{4}){3}";
    public static final BigDecimal cacheLimit = new BigDecimal(1000);
    public static final BigDecimal replenishmentLimit = new BigDecimal(1_000_000);
}
