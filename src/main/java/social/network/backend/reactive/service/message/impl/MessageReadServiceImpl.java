package social.network.backend.reactive.service.message.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.projection.MessageWithSenderAndRecipientProjection;
import social.network.backend.reactive.repository.message.MessageRepository;
import social.network.backend.reactive.service.message.MessageReadService;

@Service
@RequiredArgsConstructor
public class MessageReadServiceImpl implements MessageReadService {

    private final MessageRepository messageRepository;


    @Override
    public Mono<MessageWithSenderAndRecipientProjection> getMessageWithDetailsById(final Integer messageId) {
        return this.messageRepository
                .findMessageWithSenderAndRecipientById(messageId)
                .switchIfEmpty(Mono.error(new RuntimeException("Message not found")));
    }

    @Override
    public Flux<MessageWithSenderAndRecipientProjection> getAllMessagesByUserId(final Integer userId, final Pageable pageable) {
        return this.messageRepository.findAllMessagesByUserIdWithDetails(userId, pageable.getPageSize(), pageable.getOffset());
    }
}
