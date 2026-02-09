package social.network.backend.reactive.service.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;

@Service
@AllArgsConstructor
public final class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> prepareUserForRegistration(final Mono<User> rawUser) {
        return user -> {
            user.setRole(Role.ROLE_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return user;
        });
    }
}
