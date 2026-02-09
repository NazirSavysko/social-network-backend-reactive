package social.network.backend.reactive.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;

@Service
@AllArgsConstructor
public final class UserWriteServiceImpl implements UserWriteService {
    @Override
    public Mono<Void> deleteUser(final Integer id) {
        return null;
    }

    @Override
    public Mono<User> saveUser(final User user) {
        return null;
    }

    @Override
    public Mono<User> updateUser(final Integer id, final User user) {
        return null;
    }
}
