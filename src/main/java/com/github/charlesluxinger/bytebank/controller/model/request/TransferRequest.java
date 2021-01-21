package com.github.charlesluxinger.bytebank.controller.model.request;

import com.github.charlesluxinger.bytebank.domain.model.Transfer;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class TransferRequest {

    @NotBlank
    @Schema(example = "999.999.999-99")
    private String accountSourceId;

    @NotBlank
    @Schema(example = "999.999.999-99")
    private String accountTargetId;

    @NotNull
    @Positive
    @Schema(example = "999", minimum = "0.1")
    private BigDecimal value;

    public Transfer toDomain() {
        return Transfer
                .builder()
                .accountSourceId(this.accountSourceId)
                .accountTargetId(this.accountTargetId)
                .value(this.value)
                .build();
    }

}
