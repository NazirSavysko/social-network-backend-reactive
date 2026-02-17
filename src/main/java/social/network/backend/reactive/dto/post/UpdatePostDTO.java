package social.network.backend.reactive.dto.post;

public record UpdatePostDTO(
        String text,
        String imageInFormatBase64,
        Integer id
) {
}
