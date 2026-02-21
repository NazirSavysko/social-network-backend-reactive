package social.network.backend.reactive.repository.user;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.User;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<Boolean> existsByEmail(String email);

    Mono<User> findByEmail(String email);


    @Modifying
    @Query("DELETE FROM social_network.social_user WHERE id = :id")
    Mono<Integer> deleteUserById(Integer id);

    @Modifying
    @Query(""" 
            UPDATE social_network.social_user
                        SET name = :name, surname = :surname, email = :email, password = :password 
                        WHERE id = :id
            """)
    Mono<Integer> updateUser(Integer id, String name, String surname, String email, String password);
}
