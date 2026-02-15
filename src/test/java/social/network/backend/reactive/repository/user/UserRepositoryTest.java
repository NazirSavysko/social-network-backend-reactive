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

    @Test
    void test_updateUser_shouldReturnPositiveNumber(){
        // Given
        val userId = 1;
        val newName = "UpdatedJohn";
        val newSurname = "UpdatedDoe";
        val newEmail = "updated.john@gmail.com";
        val newPassword = "newPassword123";

        // When
        val resultMono = userRepository.updateUser(userId, newName, newSurname, newEmail, newPassword);

        // Then
        StepVerifier.create(resultMono)
                .expectNextMatches(rowsUpdated -> rowsUpdated > 0)
                .verifyComplete();
    }

    @Test
    void test_updateUser_shouldReturnZero_whenUserNotFound(){
        // Given
        val nonExistentUserId = 999;
        val newName = "UpdatedName";
        val newSurname = "UpdatedSurname";
        val newEmail = "nonexistent@mail.com";
        val newPassword = "password123";

        // When
        val resultMono = userRepository.updateUser(nonExistentUserId, newName, newSurname, newEmail, newPassword);

        // Then
        StepVerifier.create(resultMono)
                .expectNext(0)
                .verifyComplete();
    }

    @Test
    void test_updateUser_shouldUpdateAllFields(){
        // Given
        val userId = 1;
        val newName = "UpdatedJohn";
        val newSurname = "UpdatedDoe";
        val newEmail = "updated.john@gmail.com";
        val newPassword = "newEncodedPassword";

        // When
        val updateResult = userRepository.updateUser(userId, newName, newSurname, newEmail, newPassword);
        val findResult = userRepository.findById(userId);

        // Then
        StepVerifier.create(updateResult)
                .expectNext(1)
                .verifyComplete();

        StepVerifier.create(findResult)
                .expectNextMatches(user ->
                        user.getName().equals(newName) &&
                        user.getSurname().equals(newSurname) &&
                        user.getEmail().equals(newEmail) &&
                        user.getPassword().equals(newPassword)
                )
                .verifyComplete();
    }


}