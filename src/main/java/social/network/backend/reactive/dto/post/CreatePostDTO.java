package social.network.backend.reactive.dto.post;

import jakarta.validation.constraints.NotNull;
import social.network.backend.reactive.annotations.ValidImageFormat;
import social.network.backend.reactive.annotations.ValidText;

public record CreatePostDTO(
        @ValidText
        String postText,
        @NotNull(message = "{posts.create.errors.User-id.null}")
        Integer userId,
        @ValidImageFormat
        String imageInFormatBase64
) {
}
