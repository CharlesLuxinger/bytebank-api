package com.github.charlesluxinger.bytebank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Transfer {

    @NotBlank
    private String documentSource;

    @NotBlank
    private String documentTarget;

    @NotNull
    @Positive
    private BigDecimal value;

}
