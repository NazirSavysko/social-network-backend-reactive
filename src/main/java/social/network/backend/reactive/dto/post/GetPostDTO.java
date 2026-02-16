package social.network.backend.reactive.dto.post;

import java.time.Instant;

public record GetPostDTO(
        Integer likes,
        String postText,
        Instant postDate,
        Integer id,
        String image
) {
}
