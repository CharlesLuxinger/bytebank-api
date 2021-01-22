package com.github.charlesluxinger.bytebank.utils;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static com.github.charlesluxinger.bytebank.controller.model.exception.ApiExceptionResponse.buildBadRequestResponse;

public class ExceptionUtils {

    public static boolean isEquals(final Throwable err, final Class<?> clazz) {
        return err.getClass().equals(clazz);
    }

    public static Mono<ResponseEntity> errorMap(final Throwable err, final String path , final Class<? extends RuntimeException> clazz) {
        return err.getClass().equals(clazz) ? Mono.just(buildBadRequestResponse(path, err.getLocalizedMessage())) : Mono.error(err);
    }

}