package social.network.backend.reactive.service.user.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.repository.user.UserRepository;
import social.network.backend.reactive.service.user.UserReadService;

@Service
@AllArgsConstructor
public final class UserReadServiceImpl implements UserReadService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUserById(final Integer id) {
        return userRepository.findById(id);
    }
}
