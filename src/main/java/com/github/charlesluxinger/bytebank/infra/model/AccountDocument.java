package com.github.charlesluxinger.bytebank.infra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.mapping.FieldType.OBJECT_ID;


/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Document("account")
public class AccountDocument {

    @MongoId(OBJECT_ID)
    private String id;

    @NotBlank
    @Field
    private String ownerName;

    @NotBlank
    @Field
    private String document;

    @NotBlank
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal balance;

}
