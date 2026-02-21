package social.network.backend.reactive.dto.subscription;

import social.network.backend.reactive.dto.user.GetUserDTO;

public record GetSubscriptionDTO(
        Integer id,
        GetUserDTO subscriber,
        GetUserDTO subscribedTo

) {
}
