package social.network.backend.reactive.mapper.post_comment;

import social.network.backend.reactive.dto.post_comment.GetPostCommentDTO;
import social.network.backend.reactive.mapper.DtoMapper;
import social.network.backend.reactive.model.projection.PostCommentWithUserProjection;

public interface GetPostCommentDTOMapper extends DtoMapper<PostCommentWithUserProjection, GetPostCommentDTO> {
}
