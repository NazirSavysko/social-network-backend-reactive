package social.network.backend.reactive.service.user.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.repository.user.UserRepository;
import social.network.backend.reactive.service.user.UserWriteService;

@Service
@RequiredArgsConstructor
public final class UserWriteServiceImpl implements UserWriteService {

    private final UserRepository userRepository;

    @Override
    public Mono<Void> deleteUser(final Integer id) {
        return null;
    }

    @Override
    public Mono<Void> saveUser(final User user) {
        return userRepository
                .save(user)
                .then();
    }

    @Override
    public Mono<User> updateUser(final Integer id, final User user) {
        return null;
    }
}
