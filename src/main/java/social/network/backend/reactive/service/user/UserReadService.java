package social.network.backend.reactive.service.user;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;

public interface UserReadService {
    Mono<User> getUserById(Integer id);
}
