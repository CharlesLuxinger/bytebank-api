package com.github.charlesluxinger.bytebank.controller.model.request;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

import static java.math.BigDecimal.ZERO;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Schema(name = "Account Request")
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountRequest {

    @NotBlank
    @Schema(example = "João Manuel", required = true)
    private String ownerName;

    @NotBlank
    @Schema(example = "999.999.999-99", required = true)
    private String document;

    public Account toDomain() {
        return Account
                .builder()
                .ownerName(this.ownerName)
                .document(this.document)
                .balance(ZERO)
                .build();
    }
}
