package social.network.backend.reactive.repository.post;

import org.springframework.data.domain.Limit;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

import java.time.Instant;

public interface PostAnalyticRepository extends ReactiveCrudRepository<Post, Integer> {

    @Query("""
            SELECT COUNT(*)
            FROM social_network.post p
            WHERE p.post_date >= :start AND p.post_date <= :end
            """)
    Mono<Integer> countPostsByPeriod(Instant start, Instant end);

    @Query(value = """
            SELECT AVG(post_count)
            FROM (
                SELECT COUNT(*) AS post_count
                FROM social_network.post p
                WHERE p.post_date BETWEEN :start AND :end
                GROUP BY date_trunc('day', p.post_date)
            ) AS daily_counts
            """)
    Mono<Double> calculateAveragePostsPerDay(Instant start, Instant end);

    @Query("""
            SELECT
                p.id AS id,
                p.post_text AS post_text,
                p.post_date AS post_date,
                i.file_path AS image,
                (SELECT COUNT(pl.id) FROM social_network.post_like pl WHERE pl.post_id = p.id) AS likes_count
            FROM social_network.post p
            LEFT JOIN social_network.post_comment c ON p.id = c.post_id -- Исправлено имя таблицы!
            LEFT JOIN social_network.image i ON p.image_id = i.id
            WHERE p.post_date BETWEEN :start AND :end
            GROUP BY p.id, i.file_path
            ORDER BY COUNT(c.id) DESC
            LIMIT :limit
            """)
    Flux<PostWithLikesAndImageProjection> findTopTenMostCommentedPostsByPeriod(Integer limit, Instant start, Instant end);

    @Query("""
            SELECT
                p.id AS id,
                p.post_text AS post_text,
                p.post_date AS post_date,
                i.file_path AS image,
                COUNT(l.id) AS likes_count
            FROM social_network.post p
            LEFT JOIN social_network.post_like l ON p.id = l.post_id
            LEFT JOIN social_network.image i ON p.image_id = i.id
            WHERE p.post_date BETWEEN :start AND :end
            GROUP BY p.id, i.file_path
            ORDER BY COUNT(l.id) DESC, p.id DESC
            LIMIT :limit
            """)
    Flux<PostWithLikesAndImageProjection> findTopTenMostLikedPostsByPeriod(Integer limit, Instant start, Instant end);
}
