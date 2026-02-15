package social.network.backend.reactive.facade.user;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import social.network.backend.reactive.exception.UserNotFoundException;
import social.network.backend.reactive.facade.user.impl.UserProfileFacadeImpl;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.service.user.UserReadService;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class UserProfileFacadeTest {

    @Mock
    private UserReadService userReadService;

    @InjectMocks
    private UserProfileFacadeImpl userProfileFacade;

    @Test
    void test_getUserById_shouldReturnUserSuccessfully() {
        // GIVEN
        val userId = 1;
        val user = User.builder()
                .id(userId)
                .email("test@mail.com")
                .name("Test")
                .surname("User")
                .role(Role.ROLE_USER)
                .build();

        when(userReadService.getUserById(userId))
                .thenReturn(Mono.just(user));

        // WHEN
        val result = userProfileFacade.getUserById(userId);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.id() == userId &&
                        dto.email().equals("test@mail.com") &&
                        dto.name().equals("Test") &&
                        dto.surname().equals("User")
                )
                .verifyComplete();
    }

    @Test
    void test_getUserById_shouldFailWhenUserNotFound() {
        // GIVEN
        val userId = 999;

        when(userReadService.getUserById(userId))
                .thenReturn(Mono.error(new UserNotFoundException("User not found")));

        // WHEN
        val result = userProfileFacade.getUserById(userId);

        // THEN
        StepVerifier.create(result)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void test_getUserById_shouldReturnCorrectData() {
        // GIVEN
        val userId = 2;
        val user = User.builder()
                .id(userId)
                .email("john@mail.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        when(userReadService.getUserById(userId))
                .thenReturn(Mono.just(user));

        // WHEN
        val result = userProfileFacade.getUserById(userId);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.id() == 2 && dto.email().equals("john@mail.com"))
                .verifyComplete();
    }

    @Test
    void test_getUserById_shouldReturnAdminUser() {
        // GIVEN
        val userId = 3;
        val adminUser = User.builder()
                .id(userId)
                .email("admin@mail.com")
                .name("Admin")
                .surname("User")
                .role(Role.ROLE_ADMIN)
                .build();

        when(userReadService.getUserById(userId))
                .thenReturn(Mono.just(adminUser));

        // WHEN
        val result = userProfileFacade.getUserById(userId);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.id() == userId)
                .verifyComplete();
    }

    @Test
    void test_getUserById_shouldHandleMultipleUsers() {
        // GIVEN
        val user1 = User.builder()
                .id(1)
                .email("user1@mail.com")
                .name("User1")
                .surname("One")
                .role(Role.ROLE_USER)
                .build();

        val user2 = User.builder()
                .id(2)
                .email("user2@mail.com")
                .name("User2")
                .surname("Two")
                .role(Role.ROLE_USER)
                .build();

        when(userReadService.getUserById(1))
                .thenReturn(Mono.just(user1));

        when(userReadService.getUserById(2))
                .thenReturn(Mono.just(user2));

        // WHEN
        val result1 = userProfileFacade.getUserById(1);
        val result2 = userProfileFacade.getUserById(2);

        // THEN
        StepVerifier.create(result1)
                .expectNextMatches(dto -> dto.email().equals("user1@mail.com"))
                .verifyComplete();

        StepVerifier.create(result2)
                .expectNextMatches(dto -> dto.email().equals("user2@mail.com"))
                .verifyComplete();
    }
}

