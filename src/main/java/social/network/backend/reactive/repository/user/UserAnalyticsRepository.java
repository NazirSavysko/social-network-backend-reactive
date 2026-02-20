package social.network.backend.reactive.repository.user;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.model.User;

import java.time.Instant;

public interface UserAnalyticsRepository extends ReactiveCrudRepository<User, Integer> {

    @Query("""
               SELECT u.*
               FROM social_network.social_user u
               LEFT JOIN social_network.post p ON p.user_id = u.id AND p.post_date BETWEEN :start AND :end
               GROUP BY u.id
               ORDER BY COUNT(p.id) DESC
               LIMIT :limit
            """)
    Flux<User> findTopUsersByAvgPostsPerActiveDay(int limit, Instant start, Instant end);

}
