package social.network.backend.reactive.service.auth;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;
import social.network.backend.reactive.service.auth.impl.JwtGeneratorServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
final class JwtGeneratorServiceTest {

    @InjectMocks
    private JwtGeneratorServiceImpl jwtGeneratorService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtGeneratorService, "secretKey", "VerySecretKeyThatIsAtLeast32BytesLong12345");
        ReflectionTestUtils.setField(jwtGeneratorService, "expirationSeconds", 3600L);
    }

    @Test
    void test_generateToken_shouldReturnValidJWT() {
        // GIVEN
        val user = User.builder()
                .id(1)
                .email("test@mail.com")
                .name("Test")
                .surname("User")
                .password("encodedPassword")
                .role(Role.ROLE_USER)
                .build();

        // WHEN
        val token = jwtGeneratorService.generateToken(user);

        // THEN
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void test_generateToken_shouldGenerateUniqueTokens() {
        // GIVEN
        val user = User.builder()
                .id(3)
                .email("unique@mail.com")
                .name("Unique")
                .surname("User")
                .password("encodedPassword")
                .role(Role.ROLE_USER)
                .build();

        // WHEN
        val token1 = jwtGeneratorService.generateToken(user);
        val token2 = jwtGeneratorService.generateToken(user);

        // THEN
        assertNotNull(token1);
        assertNotNull(token2);
        assertNotEquals(token1, token2);
    }

    @Test
    void test_generateToken_shouldNThrowExceptionWithInValidInput() {
        // GIVEN
        val user = User.builder()
                .build();

        // WHEN & THEN
        assertThrows(NullPointerException.class,() -> jwtGeneratorService.generateToken(user));
    }
}


