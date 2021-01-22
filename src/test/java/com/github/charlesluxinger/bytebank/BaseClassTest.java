package com.github.charlesluxinger.bytebank;


import com.github.charlesluxinger.bytebank.domain.model.Account;
import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;

import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
public class BaseClassTest {

    public static final String JOAO_OWNER_NAME = "João Manuel";
    public static final String JOAO_DOCUMENT = "999.999.999-98";

    public static final String MANUEL_OWNER_NAME = "Manuel João";
    public static final String MANUEL_DOCUMENT = "999.999.999-99";

    public static AccountDocument accountDocumentBuilder(String name, String document) {
        return AccountDocument.builder().ownerName(name).document(document).build();
    }

    public static Account accountBuilder(String name, String document) {
        return Account.builder().ownerName(name).document(document).balance(BigDecimal.TEN).build();
    }

}