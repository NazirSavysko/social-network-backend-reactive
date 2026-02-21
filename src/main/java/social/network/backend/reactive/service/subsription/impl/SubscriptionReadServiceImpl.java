package social.network.backend.reactive.service.subsription.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import social.network.backend.reactive.model.projection.SubscriptionWithSubscriberAndTargetProjection;
import social.network.backend.reactive.model.projection.UserProjection;
import social.network.backend.reactive.repository.subscription.SubscriptionRepository;
import social.network.backend.reactive.service.subsription.SubscriptionReadService;

@Service
@RequiredArgsConstructor
public final class SubscriptionReadServiceImpl implements SubscriptionReadService {

    private final SubscriptionRepository subscriptionRepository;
    @Override
    public Mono<SubscriptionWithSubscriberAndTargetProjection> getSubscriptionWithDetailsById(final Integer subscriptionId) {
        return this.subscriptionRepository
                .findSubscriptionWithSubscriberAndTargetById(subscriptionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Subscription not found")));
    }

    @Override
    public Flux<SubscriptionWithSubscriberAndTargetProjection> getSubscriptionsByUserId(final Integer userId, final Pageable pageable) {
        return this.subscriptionRepository
                .findSubscriptionsWithSubscriberAndTargetBySubscriberId(userId, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public Mono<Integer> countSubscriptionsByUserId(final Integer userId) {
        return this.subscriptionRepository.countSubscriptionsByTargetId((userId));
    }

    @Override
    public Mono<Integer> countSubscriberByUserId(final Integer userId) {
        return this.subscriptionRepository.countSubscriptionsBySubscriberId(userId);
    }

    @Override
    public Flux<UserProjection> getSubscribersByUserId(final Integer userId,final Pageable pageable) {
        return this.subscriptionRepository.findSubscribersByTargetId(userId, pageable.getPageSize(), pageable.getOffset());
    }
}
