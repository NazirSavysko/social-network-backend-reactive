package social.network.backend.reactive.facade.post_comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.dto.post_comment.GetPostCommentDTO;
import social.network.backend.reactive.facade.post_comment.PostCommentFacade;
import social.network.backend.reactive.mapper.post_comment.GetPostCommentDTOMapper;
import social.network.backend.reactive.service.post_comment.PostCommentReadService;

@Component
@RequiredArgsConstructor
public final class PostCommentFacadeImpl implements PostCommentFacade {

    private final PostCommentReadService postCommentReadService;
    private final GetPostCommentDTOMapper dtoMapper;

    @Override
    public Flux<GetPostCommentDTO> getAllPostCommentsByPostId(final Integer postId, final Pageable pageable) {
        return this.postCommentReadService
                .getAllPostCommentsByPostId(postId, pageable)
                .map(this.dtoMapper::mapToDTO);
    }
}
