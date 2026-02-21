package social.network.backend.reactive.service.subsription;

import io.r2dbc.spi.Parameter;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.subscription.GetSubscriptionDTO;
import social.network.backend.reactive.model.projection.SubscriptionWithSubscriberAndTargetProjection;
import social.network.backend.reactive.model.projection.UserProjection;

public interface SubscriptionReadService {
    Mono<SubscriptionWithSubscriberAndTargetProjection> getSubscriptionWithDetailsById(Integer subscriptionId);

    Flux<SubscriptionWithSubscriberAndTargetProjection> getSubscriptionsByUserId(Integer userId, Pageable pageable);

    Mono<Integer> countSubscriptionsByUserId(Integer userId);

    Mono<Integer> countSubscriberByUserId(Integer userId);

    Flux<UserProjection> getSubscribersByUserId(Integer userId, Pageable pageable);
}
