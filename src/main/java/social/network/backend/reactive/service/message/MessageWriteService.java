package social.network.backend.reactive.service.message;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.message.GetMessageDTO;
import social.network.backend.reactive.model.Message;
import social.network.backend.reactive.model.projection.MessageWithSenderAndRecipientProjection;

public interface MessageWriteService {
    Mono<Message> saveMessage(Message message);

    Mono<Void> deleteMessageById(Integer messageId);

    Mono<MessageWithSenderAndRecipientProjection> updateMessage(Integer id, String content);
}
