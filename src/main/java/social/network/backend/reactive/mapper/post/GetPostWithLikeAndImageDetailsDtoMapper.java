package social.network.backend.reactive.mapper.post;

import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.mapper.DtoMapper;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

public interface GetPostWithLikeAndImageDetailsDtoMapper extends DtoMapper<PostWithLikesAndImageProjection, GetPostDTO> {
}
