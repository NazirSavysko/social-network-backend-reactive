package social.network.backend.reactive.service.auth;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;

public interface RegistrationService {
     Mono<User> prepareUserForRegistration(Mono<User> rawUser);
}
