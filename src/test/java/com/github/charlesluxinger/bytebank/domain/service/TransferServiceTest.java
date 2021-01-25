package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Transfer;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.TransferException;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

/**
 * @author Charles Luxinger
 * @version 24/01/21
 */
@Import(TransferServiceImpl.class)
@ExtendWith(SpringExtension.class)
class TransferServiceTest {

    @Autowired
    private TransferService service;

    @MockBean
    private AccountRepository repository;

    @Test
    @DisplayName("should return error when deposit value is a negative")
    void should_return_error_when_deposit_value_is_a_negative() {
        var transfer = Transfer.builder().accountSourceId("1").accountTargetId("2").value(BigDecimal.valueOf(-1)).build();

        StepVerifier
                .create(service.transfer(transfer))
                .expectSubscription()
                .expectError(NonPositiveValueException.class)
                .verify();
    }

    @Test
    @DisplayName("should return error when deposit value is zero")
    void should_return_error_when_deposit_value_is_zero() {
        var transfer = Transfer.builder().accountSourceId("1").accountTargetId("2").value(BigDecimal.ZERO).build();

        StepVerifier
                .create(service.transfer(transfer))
                .expectSubscription()
                .expectError(NonPositiveValueException.class)
                .verify();
    }

    @Test
    @DisplayName("should return error when source id & target id is equals")
    void should_return_error_when_source_id_and_target_id_is_equals() {
        var transfer = Transfer.builder().accountSourceId("1").accountTargetId("1").value(BigDecimal.TEN).build();

        StepVerifier
                .create(service.transfer(transfer))
                .expectSubscription()
                .expectError(TransferException.class)
                .verify();
    }

    @Test
    @DisplayName("should return a mono error when error throed")
    void should_a_mono_error_when_error_throed() {
        when(repository.findAllById(Mockito.anyCollection())).thenReturn(Flux.error(new RuntimeException()));

        var transfer = Transfer.builder().accountSourceId("1").accountTargetId("2").value(BigDecimal.ZERO).build();

        StepVerifier
                .create(service.transfer(transfer))
                .expectError(RuntimeException.class)
                .verify();
    }

}