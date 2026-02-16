package social.network.backend.reactive.mapper.post.impl;

import social.network.backend.reactive.dto.post.CreatePostDTO;
import social.network.backend.reactive.mapper.post.CreatePostEntityMapper;
import social.network.backend.reactive.model.Post;

public class CreatePostEntityMapperImpl implements CreatePostEntityMapper {
    @Override
    public Post mapToEntity(final CreatePostDTO dto) {
        return Post.builder()
                .userId(dto.userId())
                .postText(dto.postText())
                .imageId(null)
                .build();
    }
}
