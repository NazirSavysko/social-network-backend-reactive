package social.network.backend.reactive.facade.post_comment;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.dto.post_comment.GetPostCommentDTO;

public interface PostCommentFacade {

    Flux<GetPostCommentDTO> getAllPostCommentsByPostId(Integer postId, final Pageable pageable);
}
