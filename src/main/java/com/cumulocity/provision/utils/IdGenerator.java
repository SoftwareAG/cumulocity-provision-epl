package com.cumulocity.provision.utils;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator {

    private static long idCounter = 0;

    public static synchronized String createID() {
        return String.valueOf(idCounter++);
    }

    public static BigDecimal generateNumber() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, 30));
    }
}
