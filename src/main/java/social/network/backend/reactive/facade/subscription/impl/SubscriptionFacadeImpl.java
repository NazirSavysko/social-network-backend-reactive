package social.network.backend.reactive.facade.subscription.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.subscription.*;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.facade.subscription.SubscriptionFacade;
import social.network.backend.reactive.mapper.subscription.GetSubscriptionDTOMapper;
import social.network.backend.reactive.model.Subscription;
import social.network.backend.reactive.security.AccessValidator;
import social.network.backend.reactive.service.subsription.SubscriptionReadService;
import social.network.backend.reactive.service.subsription.SubscriptionWriteService;
import social.network.backend.reactive.service.user.UserReadService;

@Component
@RequiredArgsConstructor
public final class SubscriptionFacadeImpl implements SubscriptionFacade {

    private final SubscriptionWriteService subscriptionWriteService;
    private final SubscriptionReadService subscriptionReadService;
    private final UserReadService userReadService;
    private final AccessValidator accessValidator;
    private final GetSubscriptionDTOMapper dtoMapper;

    @Override
    public Mono<GetSubscriptionDTO> getSubscriptionById(final Integer subscriptionId) {
        return this.subscriptionReadService
                .getSubscriptionWithDetailsById(subscriptionId)
                .map(this.dtoMapper::mapToDTO);
    }

    @Override
    public Mono<Void> deleteSubscription(final Integer subscriptionId) {
        return this.subscriptionReadService.getSubscriptionWithDetailsById(subscriptionId)
                .map(this.dtoMapper::mapToDTO)
                .flatMap(sub -> this.accessValidator.checkSubscriptionDeleteAccess(sub, sub.subscriber().email(), sub.subscribedTo().email()))
                .flatMap(validSub -> this.subscriptionWriteService.deleteSubscriptionById(validSub.id()))
                .then();
    }

    @Override
    public Mono<GetSubscriptionDTO> updateSubscription(final Mono<UpdateSubscriptionDTO> updateSubscriptionDTO) {
        return updateSubscriptionDTO
                .flatMap(updateDTO -> this.subscriptionReadService.getSubscriptionWithDetailsById(updateDTO.id())
                        .map(this.dtoMapper::mapToDTO)
                        .flatMap(sub -> this.accessValidator.checkOwnerOrAdmin(sub, sub.subscriber().email()))
                        .flatMap(validSub -> this.subscriptionWriteService.updateSubscription(
                                updateDTO.id(),
                                updateDTO.subscriberId(),
                                updateDTO.targetId()
                        ))
                        .then(this.subscriptionReadService.getSubscriptionWithDetailsById(updateDTO.id()))
                )
                .map(this.dtoMapper::mapToDTO);

    }

    @Override
    public Mono<GetSubscriptionDTO> createSubscription(final Mono<CreateSubscriptionDTO> createSubscriptionDTOMono) {
        return createSubscriptionDTOMono
                .flatMap(create -> this.userReadService.getUserById(create.subscriberId())
                        .flatMap(user -> this.accessValidator.checkOwnerOrAdmin(user, user.getEmail()))
                        .flatMap(validUser -> {
                            val subscription = Subscription.builder()
                                    .subscriberId(create.subscriberId())
                                    .targetId(create.targetId())
                                    .build();

                            return this.subscriptionWriteService.createSubscription(subscription);
                        })
                )
                .flatMap(savedSub -> this.subscriptionReadService.getSubscriptionWithDetailsById(savedSub.id()))
                .map(this.dtoMapper::mapToDTO);
    }

    @Override
    public Flux<GetSubscriptionDTO> getSubscriptionsByUserId(final Integer userId, final Pageable pageable) {
        return this.subscriptionReadService
                .getSubscriptionsByUserId(userId, pageable)
                .map(this.dtoMapper::mapToDTO);
    }

    @Override
    public Flux<GetUserDTO> getSubscribersByUserId(final Integer userId, final Pageable pageable) {
        return this.subscriptionReadService
                .getSubscribersByUserId(userId, pageable)
                .map(userProjection -> new GetUserDTO(
                        userProjection.id(),
                        userProjection.name(),
                        userProjection.surname(),
                        userProjection.email()
                ));
    }

    @Override
    public Mono<SubscriptionsCountByUser> countSubscriptionsByUserId(final Integer userId) {
        return this.subscriptionReadService
                .countSubscriptionsByUserId(userId)
                .map(SubscriptionsCountByUser::new);
    }

    @Override
    public Mono<SubscribersCountByUser> countSubscribersByUserId(final Integer userId) {
        return this.subscriptionReadService
                .countSubscriberByUserId(userId)
                .map(SubscribersCountByUser::new);
    }
}
