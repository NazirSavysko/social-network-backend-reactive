package social.network.backend.reactive.dto.subscription;

import io.r2dbc.spi.Parameter;

public record SubscribersCountByUser(
        Integer subscribersCount
) {
}
