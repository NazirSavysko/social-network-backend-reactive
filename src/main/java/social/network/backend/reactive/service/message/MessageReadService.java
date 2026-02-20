package social.network.backend.reactive.service.message;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.projection.MessageWithSenderAndRecipientProjection;

public interface MessageReadService {
    Mono<MessageWithSenderAndRecipientProjection> getMessageWithDetailsById(Integer messageId);

    Flux<MessageWithSenderAndRecipientProjection> getAllMessagesByUserId(Integer userId, Pageable pageable);
}
