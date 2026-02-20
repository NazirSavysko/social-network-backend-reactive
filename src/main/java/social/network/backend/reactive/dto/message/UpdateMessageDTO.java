package social.network.backend.reactive.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMessageDTO(
        Integer id,
        String content
) {
}
