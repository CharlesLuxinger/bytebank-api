package com.github.charlesluxinger.bytebank.utils;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class NumberUtils {

    private static final BigDecimal TWO_THOUSAND = new BigDecimal(2000);

    public static boolean isNegativeOrZero(final BigDecimal value) {
        return value.compareTo(ZERO) > 0;
    }

    public static boolean isNegative(final BigDecimal value) {
        return value.compareTo(ZERO) >= 0;
    }

    public static boolean isLessOrEqualsTwoThousand(BigDecimal value) {
        return TWO_THOUSAND.compareTo(value) <= 0;
    }

}
