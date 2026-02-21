package social.network.backend.reactive.mapper.subscription.impl;

import org.springframework.stereotype.Component;
import social.network.backend.reactive.dto.subscription.GetSubscriptionDTO;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.mapper.subscription.GetSubscriptionDTOMapper;
import social.network.backend.reactive.model.projection.SubscriptionWithSubscriberAndTargetProjection;

@Component
public final class GetSubscriptionDTOMapperImpl implements GetSubscriptionDTOMapper {
    @Override
    public GetSubscriptionDTO mapToDTO(final SubscriptionWithSubscriberAndTargetProjection entity) {
        return new GetSubscriptionDTO(
                entity.id(),
                new GetUserDTO(
                        entity.subscriberId(),
                        entity.subscriberName(),
                        entity.subscriberSurname(),
                        entity.subscriberEmail()
                ),
                new GetUserDTO(
                        entity.targetId(),
                        entity.targetName(),
                        entity.targetSurname(),
                        entity.recipientEmail()
                )
        );
    }
}
