package social.network.backend.reactive.service.auth;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.test.StepVerifier;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.service.auth.impl.RegistrationServiceImpl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class RegistrationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void test_prepareUserForRegistration_shouldEncodePasswordAndSetRole() {
        // GIVEN
        val plainPassword = "testPassword123";
        val encodedPassword = "$2a$10$encodedPassword";
        val rawUser = User.builder()
                .id(1)
                .email("test@mail.com")
                .name("John")
                .surname("Doe")
                .password(plainPassword)
                .build();

        when(passwordEncoder.encode(plainPassword))
                .thenReturn(encodedPassword);

        // WHEN
        val result = registrationService.prepareUserForRegistration(rawUser);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(preparedUser ->
                        preparedUser.getPassword().equals(encodedPassword) &&
                        preparedUser.getRole() == Role.ROLE_USER
                )
                .verifyComplete();
    }

    @Test
    void test_prepareUserForRegistration_shouldPreserveUserData() {
        // GIVEN
        val plainPassword = "securePassword456";
        val encodedPassword = "$2a$10$secureEncodedPassword456";
        val rawUser = User.builder()
                .id(2)
                .email("john.doe@example.com")
                .name("John")
                .surname("Doe")
                .password(plainPassword)
                .build();

        when(passwordEncoder.encode(plainPassword))
                .thenReturn(encodedPassword);

        // WHEN
        val result = registrationService.prepareUserForRegistration(rawUser);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(preparedUser ->
                        preparedUser.getId() == 2 &&
                        preparedUser.getEmail().equals("john.doe@example.com") &&
                        preparedUser.getName().equals("John") &&
                        preparedUser.getSurname().equals("Doe") &&
                        preparedUser.getPassword().equals(encodedPassword) &&
                        preparedUser.getRole() == Role.ROLE_USER
                )
                .verifyComplete();
    }

    @Test
    void test_prepareUserForRegistration_shouldSetRoleUserForAllRegistrations() {
        // GIVEN
        val plainPassword = "password789";
        val encodedPassword = "$2a$10$encodedPassword789";
        val rawUser = User.builder()
                .id(3)
                .email("admin@mail.com")
                .name("Admin")
                .surname("User")
                .password(plainPassword)
                .build();

        when(passwordEncoder.encode(plainPassword))
                .thenReturn(encodedPassword);

        // WHEN
        val result = registrationService.prepareUserForRegistration(rawUser);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(preparedUser ->
                        preparedUser.getRole() == Role.ROLE_USER
                )
                .verifyComplete();
    }

    @Test
    void test_prepareUserForRegistration_shouldHandleEncodingError() {
        // GIVEN
        val plainPassword = "failPassword";
        val rawUser = User.builder()
                .id(4)
                .email("fail@mail.com")
                .name("Fail")
                .surname("User")
                .password(plainPassword)
                .build();

        when(passwordEncoder.encode(anyString()))
                .thenThrow(new RuntimeException("Encoding failed"));

        // WHEN
        val result = registrationService.prepareUserForRegistration(rawUser);

        // THEN
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}


