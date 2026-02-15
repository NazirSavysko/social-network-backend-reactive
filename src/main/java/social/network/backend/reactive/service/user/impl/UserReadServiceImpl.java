package social.network.backend.reactive.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.exception.UserExistException;
import social.network.backend.reactive.exception.UserNotFoundException;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.repository.user.UserRepository;
import social.network.backend.reactive.service.user.UserReadService;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public final class UserReadServiceImpl implements UserReadService {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String USER_EXIST_MESSAGE = "User with email %s already exists";

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUserById(final Integer id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND_MESSAGE)));
    }

    @Override
    public Mono<User> getUserByEmail(final String email) {
        return userRepository
                .findByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND_MESSAGE)));
    }

    @Override
    public Mono<Void> existsByEmail(final String email) {
        return userRepository
                .existsByEmail(email)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserExistException(format(USER_EXIST_MESSAGE, email)));
                    }
                    return Mono.empty();
                });
    }
}
