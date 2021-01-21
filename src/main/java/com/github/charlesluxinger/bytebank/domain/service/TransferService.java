package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Transfer;
import reactor.core.publisher.Mono;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public interface TransferService {

    Mono<Void> transfer(final Transfer transfer);

}
