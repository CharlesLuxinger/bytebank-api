package com.github.charlesluxinger.bytebank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {

    private String id;

    @NotBlank
    private String ownerName;

    @NotBlank
    private String document;

    @PositiveOrZero
    private BigDecimal balance;

}
