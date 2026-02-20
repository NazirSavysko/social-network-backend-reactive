package social.network.backend.reactive.dto.post;

import io.r2dbc.spi.Parameter;

public record UpdatePostDTO(
        String text,
        String imageInFormatBase64,
        String userEmail,
        Integer id
) {
}
