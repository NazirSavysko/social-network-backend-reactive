package social.network.backend.reactive.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.exception.UserNotFoundException;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.repository.user.UserRepository;
import social.network.backend.reactive.service.user.UserWriteService;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public final class UserWriteServiceImpl implements UserWriteService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id %d not found";

    private final UserRepository userRepository;

    @Override
    public Mono<Void> deleteUser(final Integer id) {
        return this.userRepository
                .deleteById(id);
    }

    @Override
    public Mono<Void> saveUser(final User user) {
        return this.userRepository
                .save(user)
                .then();
    }

    @Override
    public Mono<User> updateUser(final Integer id, final User user) {
        return this.userRepository
                .updateUser(id, user.getName(), user.getSurname(), user.getEmail(), user.getPassword())
                .flatMap(rowsUpdated -> {
                    if (rowsUpdated > 0) {
                        return Mono.just(user);
                    }
                    return Mono.error(new UserNotFoundException(format(USER_NOT_FOUND_MESSAGE, id)));
                });
    }
}
