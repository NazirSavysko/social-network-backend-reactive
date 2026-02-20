package social.network.backend.reactive.mapper.post_comment.impl;

import org.springframework.stereotype.Component;
import social.network.backend.reactive.dto.post_comment.GetPostCommentDTO;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.mapper.post_comment.GetPostCommentDTOMapper;
import social.network.backend.reactive.model.projection.PostCommentWithUserProjection;
@Component
public final class GetPostCommentDTOMapperImpl implements GetPostCommentDTOMapper {
    @Override
    public GetPostCommentDTO mapToDTO(final PostCommentWithUserProjection entity) {
        return new GetPostCommentDTO(
                entity.id(),
                entity.commentText(),
                new GetUserDTO(
                        entity.userId(),
                        entity.userName(),
                        entity.userSurname(),
                        entity.userEmail()
                )
        );
    }
}
