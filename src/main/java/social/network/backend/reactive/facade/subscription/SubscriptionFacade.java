package social.network.backend.reactive.facade.subscription;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.controller.payload.UpdateSubscriptionPayload;
import social.network.backend.reactive.dto.subscription.*;
import social.network.backend.reactive.dto.user.GetUserDTO;

public interface SubscriptionFacade {
    Mono<GetSubscriptionDTO> getSubscriptionById(Integer subscriptionId);

    Mono<Void> deleteSubscription(Integer subscriptionId);

    Mono<GetSubscriptionDTO> updateSubscription(Mono<UpdateSubscriptionDTO> updateSubscription);

    Mono<GetSubscriptionDTO> createSubscription(Mono<CreateSubscriptionDTO> createSubscriptionDTOMono);

    Flux<GetSubscriptionDTO> getSubscriptionsByUserId(Integer userId, Pageable pageable);

    Flux<GetUserDTO> getSubscribersByUserId(Integer userId, Pageable pageable);

    Mono<SubscriptionsCountByUser> countSubscriptionsByUserId(Integer userId);

    Mono<SubscribersCountByUser> countSubscribersByUserId(Integer userId);
}
