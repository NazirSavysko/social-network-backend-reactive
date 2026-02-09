package social.network.backend.reactive.service.user;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;

public interface UserWriteService {
    Mono<Void> deleteUser(Integer id);

    Mono<Void> saveUser(User user);

    Mono<User> updateUser(Integer id,User user);
}
