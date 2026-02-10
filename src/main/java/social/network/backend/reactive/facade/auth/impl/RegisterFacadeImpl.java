package social.network.backend.reactive.facade.auth.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.exception.UserExistException;
import social.network.backend.reactive.facade.auth.RegisterFacade;
import social.network.backend.reactive.mapper.auth.GetRegistrationMapper;
import social.network.backend.reactive.service.auth.RegistrationService;
import social.network.backend.reactive.service.user.UserReadService;
import social.network.backend.reactive.service.user.UserWriteService;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public final class RegisterFacadeImpl implements RegisterFacade {

    private static final String USER_EXIST_MESSAGE = "User with email %s already exists";

    private final GetRegistrationMapper registrationMapper;
    private final RegistrationService registrationService;
    private final UserReadService userReadService;
    private final UserWriteService userWriteService;

    @Override
    public Mono<Void> register(final Mono<RegisterDTO> registerFacadeDTO) {
        return registerFacadeDTO
                .map(this.registrationMapper::mapToEntity)
                .flatMap(user -> userReadService.existsByEmail(user.getEmail())
                        .then(this.registrationService.prepareUserForRegistration(user)))
                .flatMap(userWriteService::saveUser);
    }

}
