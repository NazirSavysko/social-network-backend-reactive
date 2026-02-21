package social.network.backend.reactive.mapper.subscription;

import social.network.backend.reactive.dto.subscription.GetSubscriptionDTO;
import social.network.backend.reactive.mapper.DtoMapper;
import social.network.backend.reactive.model.projection.SubscriptionWithSubscriberAndTargetProjection;

public interface GetSubscriptionDTOMapper extends DtoMapper<SubscriptionWithSubscriberAndTargetProjection, GetSubscriptionDTO> {
}
