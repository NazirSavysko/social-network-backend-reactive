package social.network.backend.reactive.repository.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {

    @Query("""
        SELECT 
            p.id, 
            p.post_text, 
            p.post_date,
            i.file_path AS image_path,
            (SELECT COUNT(*) FROM post_like pl WHERE pl.post_id = p.id) AS likes_count
        FROM post p
        LEFT JOIN image i ON p.image_id = i.id
        WHERE p.user_id = :userId
        ORDER BY p.post_date DESC
    """)
    Flux<PostWithLikesAndImageProjection> findAllByUserIdWithDetails(Integer userId, Pageable pageable);
}
