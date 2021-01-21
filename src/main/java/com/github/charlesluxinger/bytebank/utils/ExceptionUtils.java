package com.github.charlesluxinger.bytebank.utils;

public class ExceptionUtils {

    public static boolean isEquals(final Throwable err, final Class<?> clazz) {
        return err.getClass().equals(clazz);
    }

}