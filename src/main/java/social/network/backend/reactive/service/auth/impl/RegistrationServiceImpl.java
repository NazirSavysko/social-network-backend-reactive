package social.network.backend.reactive.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.service.auth.RegistrationService;

@Service
@RequiredArgsConstructor
public final class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> prepareUserForRegistration(final User rawUser) {

        return Mono.fromCallable(() -> {
                    rawUser.setPassword(passwordEncoder.encode(rawUser.getPassword()));
                    rawUser.setRole(Role.ROLE_USER);
                    return rawUser;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
