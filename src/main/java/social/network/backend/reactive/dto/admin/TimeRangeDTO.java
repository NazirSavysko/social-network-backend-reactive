package social.network.backend.reactive.dto.admin;

import java.time.Instant;

public record TimeRangeDTO(
        Instant start,
        Instant end
) {
}
