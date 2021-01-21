package com.github.charlesluxinger.bytebank.domain.model;

import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.utils.NumberUtils.isNegative;

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

    public Account balanceDecrement(final BigDecimal value) {
        var subtracted = this.balance.subtract(value);

        if (isNegative(subtracted)) {
            throw new NonPositiveValueException(value);
        }

        return Account
                .builder()
                .id(this.id)
                .ownerName(this.ownerName)
                .document(this.document)
                .balance(subtracted)
                .build();
    }

    public Account balanceIncrement(final BigDecimal value) {
        return Account
                .builder()
                .id(this.id)
                .ownerName(this.ownerName)
                .document(this.document)
                .balance(this.balance.add(value))
                .build();
    }
}
