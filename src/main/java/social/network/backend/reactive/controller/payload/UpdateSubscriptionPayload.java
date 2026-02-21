package social.network.backend.reactive.controller.payload;

import jakarta.validation.constraints.NotNull;

public record UpdateSubscriptionPayload(
        @NotNull(message = "{subscription.update.errors.Subscriber-id.null}")
        Integer subscriberId,
        @NotNull(message = "{subscription.update.errors.Target-id.null}")
        Integer targetId
) {
}
