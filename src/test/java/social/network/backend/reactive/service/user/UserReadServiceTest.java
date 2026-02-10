package social.network.backend.reactive.service.user;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import social.network.backend.reactive.exception.UserExistException;
import social.network.backend.reactive.exception.UserNotFoundException;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.repository.user.UserRepository;
import social.network.backend.reactive.service.user.impl.UserReadServiceImpl;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
final class UserReadServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserReadServiceImpl userReadService;

    @Test
    void test_getUserById_shouldReturnUser_whenFound() {
        // GIVEN
        val userId = 1;
        val mockUser = User.builder()
                .id(userId)
                .email("test@mail.com")
                .name("Test")
                .role(Role.ROLE_USER)
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Mono.just(mockUser));

        // WHEN
        val result = userReadService.getUserById(userId);

        // THEN
        StepVerifier.create(result)
                .expectNext(mockUser)
                .verifyComplete();
    }

    @Test
    void test_getUserById_shouldReturnError_whenNotFound() {
        // GIVEN
        val userId = 1;

        when(userRepository.findById(userId))
                .thenReturn(Mono.empty());

        // WHEN
        val result = userReadService.getUserById(userId);

        // THEN
        StepVerifier.create(result)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void test_getUserByEmail_shouldReturnUser_whenFound() {
        // GIVEN
        val email = "test@mail.com";
        val mockUser = User.builder()
                .id(1)
                .email(email)
                .name("Test")
                .role(Role.ROLE_USER)
                .build();

        when(userRepository.findByEmail(email))
                .thenReturn(Mono.just(mockUser));

        // WHEN
        val result = userReadService.getUserByEmail(email);

        // THEN
        StepVerifier.create(result)
                .expectNext(mockUser)
                .verifyComplete();
    }

    @Test
    void test_getUserByEmail_shouldReturnError_whenNotFound() {
        // GIVEN
        val email = "test@mail.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Mono.empty());

        // WHEN
        val result = userReadService.getUserByEmail(email);

        // THEN
        StepVerifier.create(result)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void test_existsByEmail_shouldReturnError_whenUserExists() {
        // GIVEN
        val email = "test@mail.com";

        when(userRepository.existsByEmail(email))
                .thenReturn(Mono.just(true));

        // WHEN
        val result = userReadService.existsByEmail(email);

        // THEN
        StepVerifier.create(result)
                .expectError(UserExistException.class)
                .verify();
    }

    @Test
    void test_existsByEmail_shouldCompleteSuccessfully_whenUserDoesNotExist() {
        // GIVEN
        val email = "test@mail.com";

        when(userRepository.existsByEmail(email))
                .thenReturn(Mono.just(false));

        // WHEN
        val result = userReadService.existsByEmail(email);

        // THEN
        StepVerifier.create(result)
                .verifyComplete();
    }
}