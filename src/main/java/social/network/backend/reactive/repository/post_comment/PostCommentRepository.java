package social.network.backend.reactive.repository.post_comment;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.model.PostComment;
import social.network.backend.reactive.model.projection.PostCommentWithUserProjection;

public interface PostCommentRepository extends ReactiveCrudRepository<PostComment, Integer> {

    @Query("""
            SELECT
                pc.id AS id,
                pc.comment_text AS comment_text,
                u.id AS user_id,
                u.name AS user_name,
                u.surname AS user_surname,
                u.email AS user_email
            FROM social_network.post_comment pc
            JOIN social_network.social_user u ON pc.user_id = u.id
            WHERE pc.post_id = :postId
            ORDER BY pc.comment_date DESC
            LIMIT :pageSize
            OFFSET :offset
            """)
    Flux<PostCommentWithUserProjection> getAllPostCommentsWithDetailsByPostId(Integer postId, int pageSize, long offset);
}
