package social.network.backend.reactive.controller.payload;

import social.network.backend.reactive.annotations.ValidContent;

public record UpdateMessagePayload(
        @ValidContent
        String content
) {
}
