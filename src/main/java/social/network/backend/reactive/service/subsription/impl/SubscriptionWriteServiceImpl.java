package social.network.backend.reactive.service.subsription.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Subscription;
import social.network.backend.reactive.model.projection.SubscriptionWithSubscriberAndTargetProjection;
import social.network.backend.reactive.repository.subscription.SubscriptionRepository;
import social.network.backend.reactive.service.subsription.SubscriptionWriteService;

@Service
@RequiredArgsConstructor
public final class SubscriptionWriteServiceImpl implements SubscriptionWriteService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Mono<Void> deleteSubscriptionById(final Integer subscriptionId) {
        return this.subscriptionRepository
                .deleteSubscriptionById(subscriptionId)
                .flatMap(count ->{
                    if (count == 0) return Mono.error(new RuntimeException("Subscription not found"));

                    return Mono.empty();
                });
    }

    @Override
    public Mono<SubscriptionWithSubscriberAndTargetProjection> createSubscription(final Subscription subscription) {

        return this.subscriptionRepository
                .save(subscription)
                .onErrorMap(DuplicateKeyException.class, e -> new IllegalArgumentException("You are already subscribed to this user."))
                .flatMap(savedSubscription -> this.subscriptionRepository
                        .findSubscriptionWithSubscriberAndTargetById(savedSubscription.getId())
                );
    }

    @Override
    public Mono<SubscriptionWithSubscriberAndTargetProjection> updateSubscription(final Integer id, final Integer subscriberId, final Integer targetId) {
        return this.subscriptionRepository
                .updateSubscription(id, subscriberId, targetId)
                .flatMap(count -> {
                    if (count == 0) return Mono.error(new RuntimeException("Subscription not found"));

                    return this.subscriptionRepository.findSubscriptionWithSubscriberAndTargetById(id);
                });
    }
}
