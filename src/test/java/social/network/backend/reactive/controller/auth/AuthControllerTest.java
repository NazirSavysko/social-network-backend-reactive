package social.network.backend.reactive.controller.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.AuthResponseDTO;
import social.network.backend.reactive.facade.auth.LoginFacade;
import social.network.backend.reactive.facade.auth.RegisterFacade;
import social.network.backend.reactive.validator.MonoValidator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class AuthControllerTest {

    @Mock
    private RegisterFacade registerFacade;

    @Mock
    private MonoValidator monoValidator;

    @Mock
    private LoginFacade loginFacade;

    private WebTestClient webTestClient;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        this.webTestClient = WebTestClient.bindToController(new AuthController(registerFacade, monoValidator, loginFacade)).build();
        when(monoValidator.validate(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void test_login_shouldReturnTokenPayload() {
        // GIVEN
        when(loginFacade.login(any(Mono.class)))
                .thenReturn(Mono.just(new AuthResponseDTO("jwt-token", "ROLE_USER", 7)));

        // WHEN / THEN
        this.webTestClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"email":"john@mail.com","password":"password123"}
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isEqualTo("jwt-token")
                .jsonPath("$.role").isEqualTo("ROLE_USER")
                .jsonPath("$.id").isEqualTo(7);
    }

    @Test
    void test_registration_shouldReturnOk() {
        // GIVEN
        when(registerFacade.register(any(Mono.class))).thenReturn(Mono.empty());

        // WHEN / THEN
        this.webTestClient.post()
                .uri("/api/v1/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"name":"John","surname":"Doe","email":"john@mail.com","password":"password123"}
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}
