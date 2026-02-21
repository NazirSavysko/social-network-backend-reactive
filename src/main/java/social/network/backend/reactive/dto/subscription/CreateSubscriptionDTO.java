package social.network.backend.reactive.dto.subscription;

import jakarta.validation.constraints.NotNull;

public record CreateSubscriptionDTO(
        @NotNull(message = "{subscription.create.errors.Subscriber-id.null}")
        Integer subscriberId,
        @NotNull(message = "{subscription.create.errors.Target-id.null}")
        Integer targetId
) {
}
