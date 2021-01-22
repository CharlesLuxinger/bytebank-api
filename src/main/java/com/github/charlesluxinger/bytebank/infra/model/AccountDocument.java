package com.github.charlesluxinger.bytebank.infra.model;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.infra.model.AccountDocument.ACCOUNT_DOCUMENT;
import static org.springframework.data.mongodb.core.mapping.FieldType.OBJECT_ID;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Document(ACCOUNT_DOCUMENT)
public class AccountDocument {

    public static final String ACCOUNT_DOCUMENT = "account";
    public static final String BALANCE = "balance";

    @MongoId(OBJECT_ID)
    private String id;

    @NotBlank
    @Field
    private String ownerName;

    @NotBlank
    @Field
    private String document;

    @NotNull
    @Field(name = BALANCE, targetType = FieldType.DECIMAL128)
    private BigDecimal balance;

    public static AccountDocument of(final Account account) {
        return builder()
                .id(account.getId())
                .ownerName(account.getOwnerName())
                .document(account.getDocument())
                .balance(account.getBalance())
                .build();
    }

    public Account toDomain() {
        return Account
                .builder()
                .id(this.id)
                .ownerName(this.ownerName)
                .document(this.document)
                .balance(this.balance)
                .build();
    }
}
