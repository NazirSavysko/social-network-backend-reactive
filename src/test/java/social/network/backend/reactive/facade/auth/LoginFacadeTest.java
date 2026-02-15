package social.network.backend.reactive.facade.auth;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import social.network.backend.reactive.dto.auth.GetLoginDTO;
import social.network.backend.reactive.facade.auth.impl.LoginFacadeImpl;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.service.auth.JwtGeneratorService;
import social.network.backend.reactive.service.user.UserReadService;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class LoginFacadeTest {

    @Mock
    private UserReadService userReadService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtGeneratorService jwtGeneratorService;

    @InjectMocks
    private LoginFacadeImpl loginFacade;

    @Test
    void test_login_shouldLoginUserSuccessfully() {
        // GIVEN
        val email = "john@mail.com";
        val password = "password123";
        val encodedPassword = "$2a$10$encodedPassword";
        val token = "jwtToken123";

        val user = User.builder()
                .id(1)
                .email(email)
                .name("John")
                .surname("Doe")
                .password(encodedPassword)
                .role(Role.ROLE_USER)
                .build();

        val loginDTO = new GetLoginDTO(email, password);

        when(userReadService.getUserByEmail(email))
                .thenReturn(Mono.just(user));

        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(true);

        when(jwtGeneratorService.generateToken(user))
                .thenReturn(token);

        // WHEN
        val result = loginFacade.login(Mono.just(loginDTO));

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(authResponse ->
                        authResponse.token().equals(token) &&
                        authResponse.role().equals("ROLE_USER") &&
                        authResponse.id() == 1
                )
                .verifyComplete();
    }

    @Test
    void test_login_shouldFailWithWrongPassword() {
        // GIVEN
        val email = "test@mail.com";
        val password = "wrongPassword";
        val correctPassword = "$2a$10$encodedPassword";

        val user = User.builder()
                .id(2)
                .email(email)
                .name("Test")
                .surname("User")
                .password(correctPassword)
                .role(Role.ROLE_USER)
                .build();

        val loginDTO = new GetLoginDTO(email, password);

        when(userReadService.getUserByEmail(email))
                .thenReturn(Mono.just(user));

        when(passwordEncoder.matches(password, correctPassword))
                .thenReturn(false);

        // WHEN
        val result = loginFacade.login(Mono.just(loginDTO));

        // THEN
        StepVerifier.create(result)
                .expectError(BadCredentialsException.class)
                .verify();
    }

    @Test
    void test_login_shouldFailWhenUserNotFound() {
        // GIVEN
        val email = "notfound@mail.com";
        val password = "password123";
        val loginDTO = new GetLoginDTO(email, password);

        when(userReadService.getUserByEmail(email))
                .thenReturn(Mono.error(new UsernameNotFoundException("User not found")));

        // WHEN
        val result = loginFacade.login(Mono.just(loginDTO));

        // THEN
        StepVerifier.create(result)
                .expectError(UsernameNotFoundException.class)
                .verify();
    }

    @Test
    void test_login_shouldReturnCorrectRoleForAdmin() {
        // GIVEN
        val email = "admin@mail.com";
        val password = "adminPassword";
        val encodedPassword = "$2a$10$encodedAdminPassword";
        val token = "adminToken";

        val adminUser = User.builder()
                .id(3)
                .email(email)
                .name("Admin")
                .surname("User")
                .password(encodedPassword)
                .role(Role.ROLE_ADMIN)
                .build();

        val loginDTO = new GetLoginDTO(email, password);

        when(userReadService.getUserByEmail(email))
                .thenReturn(Mono.just(adminUser));

        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(true);

        when(jwtGeneratorService.generateToken(adminUser))
                .thenReturn(token);

        // WHEN
        val result = loginFacade.login(Mono.just(loginDTO));

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(authResponse ->
                        authResponse.token().equals(token) &&
                        authResponse.role().equals("ROLE_ADMIN") &&
                        authResponse.id() == 3
                )
                .verifyComplete();
    }

    @Test
    void test_login_shouldGenerateTokenForAuthenticatedUser() {
        // GIVEN
        val email = "secure@mail.com";
        val password = "securePassword";
        val encodedPassword = "$2a$10$encodedSecurePassword";
        val token = "secureToken";

        val user = User.builder()
                .id(4)
                .email(email)
                .name("Secure")
                .surname("User")
                .password(encodedPassword)
                .role(Role.ROLE_USER)
                .build();

        val loginDTO = new GetLoginDTO(email, password);

        when(userReadService.getUserByEmail(email))
                .thenReturn(Mono.just(user));

        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(true);

        when(jwtGeneratorService.generateToken(user))
                .thenReturn(token);

        // WHEN
        val result = loginFacade.login(Mono.just(loginDTO));

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(authResponse ->
                        authResponse.token().equals(token) &&
                        authResponse.id() == 4
                )
                .verifyComplete();
    }
}




