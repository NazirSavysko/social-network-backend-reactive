package social.network.backend.reactive.controller.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.controller.payload.UpdateMessagePayload;
import social.network.backend.reactive.dto.message.GetMessageDTO;
import social.network.backend.reactive.dto.message.UpdateMessageDTO;
import social.network.backend.reactive.facade.message.MessageFacade;
import social.network.backend.reactive.validator.MonoValidator;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages/{messageId:\\d+}")
public class MessageController {

    private final MessageFacade messageFacade;
    private final MonoValidator monoValidator;

    @GetMapping
    public Mono<GetMessageDTO> getMessage(@PathVariable Integer messageId) {
        return this.messageFacade.getMessageById(messageId);
    }


    @PutMapping("/update")
    public Mono<GetMessageDTO> updateMessage(final @PathVariable Integer messageId,
                                             final @RequestBody Mono<UpdateMessagePayload> content) {

        return content
                .transform(this.monoValidator::validate)
                .map(payload -> new UpdateMessageDTO(
                        messageId,
                        payload.content()
                ))
                .as(this.messageFacade::updateMessage);
    }

    @DeleteMapping("/delete")
    public Mono<Void> deleteMessage(final @PathVariable Integer messageId) {
        return this.messageFacade.deleteMessage(messageId);
    }

}