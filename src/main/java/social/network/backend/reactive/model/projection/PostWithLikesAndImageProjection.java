package social.network.backend.reactive.model.projection;

import java.time.Instant;

public record PostWithLikesAndImageProjection(
        Integer id,
        String postText,
        Instant postDate,
        String image,
        Integer likesCount
) {
}
