package com.github.charlesluxinger.bytebank.controller.model.response;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Schema(name = "Account Response")
@Builder
@Getter
public class AccountResponse {

    @Schema(example = "507f1f77bcf86cd799439011")
    private final String id;

    @Schema(example = "João Manuel")
    private final String ownerName;

    @NotBlank
    @Schema(example = "999.999.999-99")
    private final String document;

    public static AccountResponse of(final Account account) {
        return AccountResponse
                .builder()
                .id(account.getId())
                .ownerName(account.getOwnerName())
                .document(account.getDocument())
                .build();
    }
}
