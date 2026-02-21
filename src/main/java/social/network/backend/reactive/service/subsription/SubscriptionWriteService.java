package social.network.backend.reactive.service.subsription;

import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Subscription;
import social.network.backend.reactive.model.projection.SubscriptionWithSubscriberAndTargetProjection;

public interface SubscriptionWriteService {
    Mono<Void> deleteSubscriptionById(Integer subscriptionId);

    Mono<SubscriptionWithSubscriberAndTargetProjection> createSubscription(Subscription subscription);

    Mono<SubscriptionWithSubscriberAndTargetProjection> updateSubscription(Integer id, Integer integer, Integer integer1);
}
