package com.github.charlesluxinger.bytebank.controller.model.response;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Builder
@Getter
public class AccountResponse {

    private final String id;
    private final String ownerName;
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
