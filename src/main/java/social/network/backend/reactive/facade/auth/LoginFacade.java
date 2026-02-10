package social.network.backend.reactive.facade.auth;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.AuthResponseDTO;
import social.network.backend.reactive.dto.auth.GetLoginDTO;

@FunctionalInterface
public interface LoginFacade {
    Mono<AuthResponseDTO> login(Mono<GetLoginDTO> registerDTO);
}
