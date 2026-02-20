package social.network.backend.reactive.controller.payload;

import social.network.backend.reactive.annotations.ValidImageFormat;
import social.network.backend.reactive.annotations.ValidText;

public record UpdatePostPayload(
        @ValidText
        String text,
        @ValidImageFormat
        String imageInFormatBase64
) {
}
