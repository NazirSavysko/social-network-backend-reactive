package social.network.backend.reactive.repository.post;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

import java.time.Instant;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {

    @Modifying
    @Query("DELETE FROM social_network.post WHERE id = :id")
    Mono<Integer> deletePostById(Integer id);

    @Query("""
                    SELECT
                        p.id,
                        p.post_text,
                        p.post_date,
                        i.file_path AS image,
                        (SELECT COUNT(*) FROM social_network.post_like pl WHERE pl.post_id = p.id) AS likes_count
                    FROM social_network.post p
                    JOIN social_network.image i ON p.image_id = i.id
                    WHERE p.user_id = :userId
                    ORDER BY p.post_date DESC
               LIMIT :limit
                OFFSET :offset
            """)
    Flux<PostWithLikesAndImageProjection> findAllByUserIdWithDetails(Integer userId, long limit, long offset);

    @Query("""
                    SELECT
                        p.id,
                        p.post_text,
                        p.post_date,
                        i.file_path AS image,
                        (SELECT COUNT(*) FROM social_network.post_like pl WHERE pl.post_id = p.id) AS likes_count
                    FROM social_network.post p
                    JOIN social_network.image i ON p.image_id = i.id
                    WHERE p.id = :postId
            """)
    Mono<PostWithLikesAndImageProjection> findByIdWithDetails(Integer postId);

    @Modifying
    @Query("""
            UPDATE social_network.post
                        SET post_text = :content, post_date = :data, image_id = :imageId
                        WHERE id = :id
            """)
    Mono<Integer> updatePost(Integer id, String content, Instant data, Integer imageId);

}
