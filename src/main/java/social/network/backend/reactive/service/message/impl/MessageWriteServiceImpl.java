package social.network.backend.reactive.service.message.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Message;
import social.network.backend.reactive.model.projection.MessageWithSenderAndRecipientProjection;
import social.network.backend.reactive.repository.message.MessageRepository;
import social.network.backend.reactive.service.message.MessageWriteService;


@Service
@RequiredArgsConstructor
public final class MessageWriteServiceImpl implements MessageWriteService {

    private final MessageRepository messageRepository;

    @Override
    public Mono<Message> saveMessage(final Message message) {
        return this.messageRepository.save(message);
    }

    @Override
    public Mono<Void> deleteMessageById(final Integer messageId) {
        return this.messageRepository.deleteSubscriptionById(messageId)
                .flatMap(count ->{
                    if (count == 0) return Mono.error(new RuntimeException("Message not found"));

                    return Mono.empty();
                });

    }

    @Override
    public Mono<MessageWithSenderAndRecipientProjection> updateMessage(final Integer id, final String content) {
        return this.messageRepository.updateMessageContent(id, content)
                .flatMap(count -> {
                    if (count == 0) return Mono.error(new RuntimeException("Message not found"));

                    return this.messageRepository.findMessageWithSenderAndRecipientById(id);
                });
    }
}
