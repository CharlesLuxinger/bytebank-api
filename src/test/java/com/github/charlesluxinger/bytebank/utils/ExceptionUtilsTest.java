package com.github.charlesluxinger.bytebank.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionUtilsTest {

    @Test
    @DisplayName("should return false when is different class")
    void should_return_false_when_is_different_class() {
        assertFalse(ExceptionUtils.isEquals(new RuntimeException(), Exception.class));
    }

    @Test
    @DisplayName("should return true when is the sam class")
    void should_return_false_when_is_equal_class() {
        assertTrue(ExceptionUtils.isEquals(new RuntimeException(), RuntimeException.class));
    }

    @Test
    @DisplayName("should return a mono error with exception specific when the exception is equal")
    void should_return_a_mono_error_with_exception_specific_when_the_exception_is_equal() {
        var mono = ExceptionUtils.errorMap(new RuntimeException(), "/path", RuntimeException.class);

        StepVerifier
                .create(mono)
                .expectSubscription()
                .expectError(RuntimeException.class);
    }

    @Test
    @DisplayName("should return a mono error with exception specific when the exception is equal")
    void should_return_a_mono_error_with_exception_throed_when_the_exception_is_different() {
        var mono = ExceptionUtils.errorMap(new Exception(), "/path", RuntimeException.class);

        StepVerifier
                .create(mono)
                .expectSubscription()
                .expectError(Exception.class);
    }

}