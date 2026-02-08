package social.network.backend.reactive.repository.user;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<Boolean> existsByEmail(String email);

    Mono<User> findByEmail(String email);
}
