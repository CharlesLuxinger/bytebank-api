package com.github.charlesluxinger.bytebank.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberUtilsTest {

    @Test
    @DisplayName("should return false when is positive value")
    void should_return_false_when_is_positive_value_isNegativeOrZero() {
        assertFalse(NumberUtils.isNegativeOrZero(BigDecimal.TEN));
    }

    @Test
    @DisplayName("should return true when is negative value")
    void should_return_true_when_is_negative_value_isNegativeOrZero() {
        assertTrue(NumberUtils.isNegativeOrZero(BigDecimal.valueOf(-1)));
    }

    @Test
    @DisplayName("should return true when is zero")
    void should_return_true_when_is_zero_isNegativeOrZero() {
        assertTrue(NumberUtils.isNegativeOrZero(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("should return false when is positive value")
    void should_return_false_when_is_positive_value_isNegative() {
        assertFalse(NumberUtils.isNegative(BigDecimal.TEN));
    }

    @Test
    @DisplayName("should return false when is zero")
    void should_return_false_when_is_zero_isNegative() {
        assertFalse(NumberUtils.isNegative(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("should return true when is negative value")
    void should_return_true_when_is_negative_value_isNegative() {
        assertTrue(NumberUtils.isNegativeOrZero(BigDecimal.valueOf(-1)));
    }

    @Test
    @DisplayName("should return false when is less than two thousand")
    void should_return_false_when_is_less_than_two_thousand() {
        assertFalse(NumberUtils.isGreaterThanTwoThousand(BigDecimal.valueOf(1999.99)));
    }

    @Test
    @DisplayName("should return false when is equals to two thousand")
    void should_return_false_when_equals_to_two_thousand() {
        assertFalse(NumberUtils.isGreaterThanTwoThousand(BigDecimal.valueOf(2000)));
    }

    @Test
    @DisplayName("should return true when is greater than two thousand")
    void should_return_true_when_is_greater_than_two_thousand() {
        assertTrue(NumberUtils.isGreaterThanTwoThousand(BigDecimal.valueOf(2000.01)));
    }

}