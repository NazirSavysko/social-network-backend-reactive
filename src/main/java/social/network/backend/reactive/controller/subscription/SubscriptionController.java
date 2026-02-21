package social.network.backend.reactive.controller.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.controller.payload.UpdateSubscriptionPayload;
import social.network.backend.reactive.dto.subscription.GetSubscriptionDTO;
import social.network.backend.reactive.dto.subscription.UpdateSubscriptionDTO;
import social.network.backend.reactive.facade.subscription.SubscriptionFacade;
import social.network.backend.reactive.validator.MonoValidator;

@RestController
@RequestMapping("/api/v1/subscriptions/{subscriptionId:\\d+}")
@RequiredArgsConstructor
public final class SubscriptionController {

    private final SubscriptionFacade subscriptionFacade;
    private final MonoValidator monoValidator;

    @GetMapping
    public Mono<GetSubscriptionDTO> getSubscription(final @PathVariable Integer subscriptionId) {
        return this.subscriptionFacade.getSubscriptionById(subscriptionId);
    }

    @PutMapping("/update")
    public  Mono<GetSubscriptionDTO> updateSubscription(final @PathVariable Integer subscriptionId,
                                                final @RequestBody Mono<UpdateSubscriptionPayload> updateSubscriptionPayload) {
        return updateSubscriptionPayload
                .transform(this.monoValidator::validate)
                .map(payload -> new UpdateSubscriptionDTO(
                        subscriptionId,
                        payload.subscriberId(),
                        payload.targetId()
                ))
                .as(this.subscriptionFacade::updateSubscription);
    }

    @DeleteMapping("/delete")
    public Mono<Void> deleteSubscription(final @PathVariable Integer subscriptionId) {
        return this.subscriptionFacade.deleteSubscription(subscriptionId);
    }
}
