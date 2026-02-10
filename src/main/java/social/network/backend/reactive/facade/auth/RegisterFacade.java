package social.network.backend.reactive.facade.auth;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.RegisterDTO;

@FunctionalInterface
public interface RegisterFacade {
    Mono<Void> register(Mono<RegisterDTO> registerFacadeDTO);
}
