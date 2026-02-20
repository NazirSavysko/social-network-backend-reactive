package social.network.backend.reactive.service.post_comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.model.projection.PostCommentWithUserProjection;
import social.network.backend.reactive.repository.post_comment.PostCommentRepository;
import social.network.backend.reactive.service.post_comment.PostCommentReadService;

@Service
@RequiredArgsConstructor
public final class PostCommentReadServiceImpl implements PostCommentReadService {

    private final PostCommentRepository postCommentRepository;

    @Override
    public Flux<PostCommentWithUserProjection> getAllPostCommentsByPostId(final Integer postId, final Pageable pageable) {
        return postCommentRepository.getAllPostCommentsWithDetailsByPostId(postId, pageable.getPageSize(), pageable.getOffset());
    }
}
