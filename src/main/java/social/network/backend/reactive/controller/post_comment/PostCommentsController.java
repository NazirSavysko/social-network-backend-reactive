package social.network.backend.reactive.controller.post_comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.dto.post_comment.GetPostCommentDTO;
import social.network.backend.reactive.facade.post_comment.PostCommentFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-comments")
public final class PostCommentsController {

    private final PostCommentFacade postCommentFacade;

    @GetMapping("/post/{postId:\\d+}")
    public Flux<GetPostCommentDTO> getAllPostCommentsByPostId(final @PathVariable Integer postId, final Pageable pageable) {

        return this.postCommentFacade.getAllPostCommentsByPostId(postId, pageable);
    }
}
