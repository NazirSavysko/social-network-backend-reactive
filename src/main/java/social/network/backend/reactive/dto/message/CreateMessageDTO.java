package social.network.backend.reactive.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import social.network.backend.reactive.annotations.ValidContent;

public record CreateMessageDTO(
        @ValidContent
        String content,
        @NotNull(message = "{messages.create.errors.sender_id_is_null}")
        Integer senderId,
        @NotNull(message = "{messages.create.errors.receiver_id_is_null}")
        Integer receiverId
) {
}
