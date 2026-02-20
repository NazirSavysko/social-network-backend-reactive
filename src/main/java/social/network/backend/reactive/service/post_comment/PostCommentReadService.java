package social.network.backend.reactive.service.post_comment;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.model.projection.PostCommentWithUserProjection;

public interface PostCommentReadService {
   Flux<PostCommentWithUserProjection> getAllPostCommentsByPostId(Integer postId, Pageable pageable);
}
