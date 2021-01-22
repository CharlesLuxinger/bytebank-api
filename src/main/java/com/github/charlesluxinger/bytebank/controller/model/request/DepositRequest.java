package com.github.charlesluxinger.bytebank.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
@Schema(name = "Deposit Request")
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class DepositRequest {

    @NotNull
    @Schema(example = "999", minimum = "0.1", required = true)
    private BigDecimal value;

}
