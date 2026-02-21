package social.network.backend.reactive.controller.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.subscription.CreateSubscriptionDTO;
import social.network.backend.reactive.dto.subscription.GetSubscriptionDTO;
import social.network.backend.reactive.dto.subscription.SubscribersCountByUser;
import social.network.backend.reactive.dto.subscription.SubscriptionsCountByUser;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.facade.subscription.SubscriptionFacade;
import social.network.backend.reactive.validator.MonoValidator;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public final class SubscriptionsController {

    private final SubscriptionFacade subscriptionFacade;
    private final MonoValidator monoValidator;

    @PostMapping("/create")
    public Mono<ResponseEntity<?>> createSubscription(final @RequestBody Mono<CreateSubscriptionDTO> createSubscriptionDTO,
                                           final UriComponentsBuilder uriComponentsBuilder) {
       return createSubscriptionDTO
               .transform(this.monoValidator::validate)
               .as(this.subscriptionFacade::createSubscription)
               .map(getSubscriptionDto -> ResponseEntity.created(
                       uriComponentsBuilder.replacePath("/api/v1/subscriptions/{id}")
                               .build(getSubscriptionDto.id())
               ).body(getSubscriptionDto));
    }

    @GetMapping("/user/{userId:\\d+}/subscriptions/list")
    public Flux<GetSubscriptionDTO> getSubscriptionsListByUserId(@PathVariable Integer userId, final  Pageable pageable) {
        return this.subscriptionFacade.getSubscriptionsByUserId(userId, pageable);
    }

    @GetMapping("/user/{userId:\\d+}/subscribers/list")
    public Flux<GetUserDTO> getSubscribersListByUserId(@PathVariable Integer userId, final Pageable pageable) {
        return this.subscriptionFacade.getSubscribersByUserId(userId, pageable);
    }

    @GetMapping("/user/{userId:\\d+}/subscriptions/count")
    public Mono<SubscriptionsCountByUser> getSubscriptionsCountByUserId(@PathVariable Integer userId) {
        return this.subscriptionFacade.countSubscriptionsByUserId(userId);
    }

    @GetMapping("/user/{userId:\\d+}/subscribers/count")
    public Mono<SubscribersCountByUser> getSubscribersCountByUserId(@PathVariable Integer userId) {
        return this.subscriptionFacade.countSubscribersByUserId(userId);
    }
}
