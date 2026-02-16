package social.network.backend.reactive.mapper.post.impl;

import org.springframework.stereotype.Component;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.mapper.post.GetPostWithLikeAndImageDetailsDtoMapper;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

@Component
public class GetPostWithLikeAndImageDetailsDtoMapperImpl implements GetPostWithLikeAndImageDetailsDtoMapper {
    @Override
    public GetPostDTO mapToDTO(final PostWithLikesAndImageProjection entity) {
        return new GetPostDTO(
                entity.likesCount(),
                entity.postText(),
                entity.postDate(),
                entity.id(),
                entity.image()
        );
    }
}
