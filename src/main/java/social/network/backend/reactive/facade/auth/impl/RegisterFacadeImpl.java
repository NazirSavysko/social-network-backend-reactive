package social.network.backend.reactive.facade.auth.impl;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.facade.auth.RegisterFacade;

@Component
public final class RegisterFacadeImpl implements RegisterFacade {
    @Override
    public Mono<Void> register(final RegisterDTO registerFacadeDTO) {
        return Mono.empty();
    }
}
