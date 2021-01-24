package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.exeception.GreaterThanDepositValueException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

/**
 * @author Charles Luxinger
 * @version 24/01/21
 */
@Import(DepositServiceImpl.class)
@ExtendWith(SpringExtension.class)
class DepositServiceTest {

    @Autowired
    private DepositService service;

    @MockBean
    private AccountRepository repository;

    @Test
    @DisplayName("should deposit when is a positive value")
    void should_deposit_when_is_a_positive_value(){
        when(repository.deposit("1", BigDecimal.TEN))
                .thenReturn(Mono.empty());

        StepVerifier
                .create(service.deposit("1", BigDecimal.TEN))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("should return error when deposit value is a negative")
    void should_return_error_when_deposit_value_is_a_negative(){
        StepVerifier
                .create(service.deposit("1", BigDecimal.valueOf(-1)))
                .expectSubscription()
                .expectError(NonPositiveValueException.class)
                .verify();
    }

    @Test
    @DisplayName("should return error when deposit value is zero")
    void should_return_error_when_deposit_value_is_zero(){
        StepVerifier
                .create(service.deposit("1", BigDecimal.valueOf(0)))
                .expectSubscription()
                .expectError(NonPositiveValueException.class)
                .verify();
    }

    @Test
    @DisplayName("should return error when deposit value is greater the two thousand")
    void should_return_error_when_deposit_value_is_greater_then_two_thousand(){
        StepVerifier
                .create(service.deposit("1", BigDecimal.valueOf(2000.1)))
                .expectSubscription()
                .expectError(GreaterThanDepositValueException.class)
                .verify();
    }

}
