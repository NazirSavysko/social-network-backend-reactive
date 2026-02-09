package social.network.backend.reactive.facade.auth.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.facade.auth.RegisterFacade;
import social.network.backend.reactive.mapper.auth.GetRegistrationMapper;
import social.network.backend.reactive.service.auth.RegistrationService;
import social.network.backend.reactive.service.user.UserWriteService;

@Component
@AllArgsConstructor
public final class RegisterFacadeImpl implements RegisterFacade {

    private final GetRegistrationMapper registrationMapper;
    private final RegistrationService registrationService;
    private final UserWriteService userWriteService;

    @Override
    public Mono<Void> register(final Mono<RegisterDTO> registerFacadeDTO) {
        return registerFacadeDTO
                .map(this.registrationMapper::mapToEntity)
                .flatMap(this.registrationService::prepareUserForRegistration)
                .flatMap(this.userWriteService::saveUser);
    }
}
