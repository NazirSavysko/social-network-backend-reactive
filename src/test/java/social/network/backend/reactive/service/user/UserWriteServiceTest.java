package social.network.backend.reactive.service.user;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.repository.user.UserRepository;
import social.network.backend.reactive.service.user.impl.UserWriteServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class UserWriteServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserWriteServiceImpl userWriteService;

    @Test
    void test_saveUser_shouldSaveUserSuccessfully() {
        // GIVEN
        val user = User.builder()
                .id(1)
                .email("test@mail.com")
                .name("Test")
                .surname("User")
                .password("password123")
                .role(Role.ROLE_USER)
                .build();

        when(userRepository.save(any(User.class)))
                .thenReturn(Mono.just(user));

        // WHEN
        val result = userWriteService.saveUser(user);

        // THEN
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void test_saveUser_shouldHandleRepositoryError() {
        // GIVEN
        val user = User.builder()
                .id(1)
                .email("test@mail.com")
                .name("Test")
                .surname("User")
                .password("password123")
                .role(Role.ROLE_USER)
                .build();

        when(userRepository.save(any(User.class)))
                .thenReturn(Mono.error(new RuntimeException("Database error")));

        // WHEN
        val result = userWriteService.saveUser(user);

        // THEN
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void test_prepareUser_shouldEncodePasswordSuccessfully() {
        // GIVEN
        val plainPassword = "password123";
        val encodedPassword = "$2a$10$encodedPassword";
        val user = User.builder()
                .id(1)
                .email("test@mail.com")
                .name("Test")
                .surname("User")
                .password(plainPassword)
                .role(Role.ROLE_USER)
                .build();

        when(passwordEncoder.encode(plainPassword))
                .thenReturn(encodedPassword);

        // WHEN
        val result = userWriteService.prepareUser(user);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(preparedUser -> preparedUser.getPassword().equals(encodedPassword))
                .verifyComplete();
    }

    @Test
    void test_prepareUser_shouldReturnUserWithEncodedPassword() {
        // GIVEN
        val plainPassword = "myPassword456";
        val encodedPassword = "$2a$10$encodedPassword456";
        val user = User.builder()
                .id(2)
                .email("user2@mail.com")
                .name("John")
                .surname("Doe")
                .password(plainPassword)
                .role(Role.ROLE_USER)
                .build();

        when(passwordEncoder.encode(plainPassword))
                .thenReturn(encodedPassword);

        // WHEN
        val result = userWriteService.prepareUser(user);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(preparedUser ->
                        preparedUser.getId() == 2 &&
                        preparedUser.getEmail().equals("user2@mail.com") &&
                        preparedUser.getName().equals("John") &&
                        preparedUser.getPassword().equals(encodedPassword)
                )
                .verifyComplete();
    }
}
