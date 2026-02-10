package social.network.backend.reactive.repository.user;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import reactor.test.StepVerifier;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.enums.Role;

@DataR2dbcTest
final class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;



    @Test
    void test_findByEmail_shouldReturnUser() {
        //Given
        val email = "admin@social.net";

        //When
        val resultMono = userRepository.findByEmail(email);

        //Then
        StepVerifier.create(resultMono)
                .expectNextMatches(user ->
                        user.getName().equals("Admin") &&
                                user.getRole() == Role.ROLE_ADMIN &&
                                user.getId() != null
                )
                .verifyComplete();
    }

    @Test
    void test_findByEmail_shouldReturnEmpty() {
        //Given
        val email = "not_exists@mail.com";

        //When
        val resultMono = userRepository.findByEmail(email);

        //Then
        StepVerifier.create(resultMono)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void test_saveUser_shouldReturnUser(){
        //Given
         val testUser =       User.builder()
                        .name("Admin")
                        .role(Role.ROLE_ADMIN)
                        .email("sdsdsd")
                        .surname("Admin")
                        .password("ssdssds")
                        .build();

        //When
        val userMono = this.userRepository.save(testUser);

        //Then
        StepVerifier.create(userMono)
                .expectNextMatches(user ->
                        user.getName().equals("Admin") &&
                                user.getRole() == Role.ROLE_ADMIN &&
                                user.getId() != null
                )
                .verifyComplete();
    }

    @Test
    void test_saveUser_duplicateEmail_shouldThrowError() {
        // Given
        val duplicateUser = User.builder()
                .name("Hacker")
                .surname("BlackHat")
                .email("admin@social.net")
                .password("12345")
                .role(Role.ROLE_USER)
                .build();

        // When
        val resultMono = userRepository.save(duplicateUser);

        // Then
        StepVerifier.create(resultMono)
                .expectError()
                .verify();
    }


}