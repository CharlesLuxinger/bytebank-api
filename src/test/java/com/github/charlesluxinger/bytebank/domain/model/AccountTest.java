package com.github.charlesluxinger.bytebank.domain.model;

import com.github.charlesluxinger.bytebank.domain.model.exeception.InsufficientBalanceAmountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.BaseClassTest.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    @DisplayName("should decrement an account balance")
    void should_decrement_an_account_balance() {
        var actual = accountBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);
        var expected = actual.balanceDecrement(BigDecimal.ONE);

        assertNotSame(actual, expected);
        assertEquals(actual.getBalance(), BigDecimal.TEN);
        assertEquals(expected.getBalance(), BigDecimal.valueOf(9));
    }

    @Test
    @DisplayName("should trow insufficient balance amount exception decrement an account with insufficient balance")
    void should_trow_insufficient_balance_amount_exception_decrement_an_account_with_insufficient_balance() {
        assertThrows(InsufficientBalanceAmountException.class,
                () -> accountBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT).balanceDecrement(BigDecimal.valueOf(11)),
                "Insufficient balance to transfer the amount: 11.");
    }

    @Test
    @DisplayName("should increment an account balance")
    void should_increment_an_account_balance() {
        var actual = accountBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);
        var expected = actual.balanceIncrement(BigDecimal.ONE);

        assertNotSame(actual, expected);
        assertEquals(actual.getBalance(), BigDecimal.TEN);
        assertEquals(expected.getBalance(), BigDecimal.valueOf(11));
    }

}