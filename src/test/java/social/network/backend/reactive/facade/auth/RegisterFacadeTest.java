package social.network.backend.reactive.facade.auth;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.exception.UserExistException;
import social.network.backend.reactive.facade.auth.impl.RegisterFacadeImpl;
import social.network.backend.reactive.mapper.auth.GetRegistrationEntityMapper;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.service.auth.RegistrationService;
import social.network.backend.reactive.service.user.UserReadService;
import social.network.backend.reactive.service.user.UserWriteService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class RegisterFacadeTest {

    @Mock
    private GetRegistrationEntityMapper registrationMapper;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private UserReadService userReadService;

    @Mock
    private UserWriteService userWriteService;

    @InjectMocks
    private RegisterFacadeImpl registerFacade;

    @Test
    void test_register_shouldRegisterUserSuccessfully() {
        // GIVEN
        val registerDTO = new RegisterDTO(
                "John",
                "Doe",
                "john@mail.com",
                "password123"
        );

        val mappedUser = User.builder()
                .name("John")
                .surname("Doe")
                .email("john@mail.com")
                .password("password123")
                .build();

        val preparedUser = User.builder()
                .name("John")
                .surname("Doe")
                .email("john@mail.com")
                .password("encodedPassword")
                .role(Role.ROLE_USER)
                .build();

        when(registrationMapper.mapToEntity(registerDTO))
                .thenReturn(mappedUser);

        when(userReadService.existsByEmail(any(String.class)))
                .thenReturn(Mono.empty());

        when(registrationService.prepareUserForRegistration(any(User.class)))
                .thenReturn(Mono.just(preparedUser));

        when(userWriteService.saveUser(any(User.class)))
                .thenReturn(Mono.empty());

        // WHEN
        val result = registerFacade.register(Mono.just(registerDTO));

        // THEN
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void test_register_shouldFailWhenUserExists() {
        // GIVEN
        val registerDTO = new RegisterDTO(
                "Jane",
                "Smith",
                "jane@mail.com",
                "password456"
        );

        val mappedUser = User.builder()
                .name("Jane")
                .surname("Smith")
                .email("jane@mail.com")
                .password("password456")
                .build();

        when(registrationMapper.mapToEntity(registerDTO))
                .thenReturn(mappedUser);

        when(userReadService.existsByEmail(any(String.class)))
                .thenReturn(Mono.error(new UserExistException("User already exists")));
         when(registrationService.prepareUserForRegistration(any(User.class)))
                 .thenReturn(Mono.empty());
        // WHEN
        val result = registerFacade.register(Mono.just(registerDTO));

        // THEN
        StepVerifier.create(result)
                .expectError(UserExistException.class)
                .verify();
    }

    @Test
    void test_register_shouldPrepareUserBeforeSaving() {
        // GIVEN
        val registerDTO = new RegisterDTO(
                "Bob",
                "Wilson",
                "bob@mail.com",
                "password789"
        );

        val mappedUser = User.builder()
                .name("Bob")
                .surname("Wilson")
                .email("bob@mail.com")
                .password("password789")
                .build();

        val preparedUser = User.builder()
                .name("Bob")
                .surname("Wilson")
                .email("bob@mail.com")
                .password("encodedPassword789")
                .role(Role.ROLE_USER)
                .build();

        when(registrationMapper.mapToEntity(any(RegisterDTO.class)))
                .thenReturn(mappedUser);

        when(userReadService.existsByEmail(any(String.class)))
                .thenReturn(Mono.empty());

        when(registrationService.prepareUserForRegistration(any(User.class)))
                .thenReturn(Mono.just(preparedUser));

        when(userWriteService.saveUser(any(User.class)))
                .thenReturn(Mono.empty());

        // WHEN
        val result = registerFacade.register(Mono.just(registerDTO));

        // THEN
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void test_register_shouldHandleSaveUserError() {
        // GIVEN
        val registerDTO = new RegisterDTO(
                "Error",
                "User",
                "error@mail.com",
                "password000"
        );

        val mappedUser = User.builder()
                .name("Error")
                .surname("User")
                .email("error@mail.com")
                .password("password000")
                .build();

        val preparedUser = User.builder()
                .name("Error")
                .surname("User")
                .email("error@mail.com")
                .password("encodedPassword000")
                .role(Role.ROLE_USER)
                .build();

        when(registrationMapper.mapToEntity(any(RegisterDTO.class)))
                .thenReturn(mappedUser);

        when(userReadService.existsByEmail(any(String.class)))
                .thenReturn(Mono.empty());

        when(registrationService.prepareUserForRegistration(any(User.class)))
                .thenReturn(Mono.just(preparedUser));

        when(userWriteService.saveUser(any(User.class)))
                .thenReturn(Mono.error(new RuntimeException("Save failed")));

        // WHEN
        val result = registerFacade.register(Mono.just(registerDTO));

        // THEN
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}


