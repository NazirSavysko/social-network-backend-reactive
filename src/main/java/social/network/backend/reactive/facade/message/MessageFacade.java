package social.network.backend.reactive.facade.message;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.controller.payload.UpdateMessagePayload;
import social.network.backend.reactive.dto.message.CreateMessageDTO;
import social.network.backend.reactive.dto.message.GetMessageDTO;
import social.network.backend.reactive.dto.message.UpdateMessageDTO;

public interface MessageFacade {
    Flux<GetMessageDTO> getAllMessagesByUserId(Integer userId, Pageable pageable);

    Mono<GetMessageDTO> createMessage(Mono<CreateMessageDTO> createMessageDTOMono);

    Mono<GetMessageDTO> getMessageById(Integer messageId);

    Mono<GetMessageDTO> updateMessage(Mono<UpdateMessageDTO> updateMessagePayloadMono);

    Mono<Void> deleteMessage(Integer messageId);
}
