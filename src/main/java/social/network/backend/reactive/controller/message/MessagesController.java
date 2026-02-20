package social.network.backend.reactive.controller.message;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.message.CreateMessageDTO;
import social.network.backend.reactive.dto.message.GetMessageDTO;
import social.network.backend.reactive.facade.message.MessageFacade;
import social.network.backend.reactive.validator.MonoValidator;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public final class MessagesController {

    private final MessageFacade messageFacade;
    private final MonoValidator monoValidator;

    @PostMapping("/create")
    public Mono<ResponseEntity<?>> createMessage(final @RequestBody Mono<CreateMessageDTO> createMessageDTO,
                                                 final UriComponentsBuilder uriComponentsBuilder) {

        return createMessageDTO
                .transform(this.monoValidator::validate)
                .as(this.messageFacade::createMessage)
                .map(getMessageDTO -> ResponseEntity.created(
                        uriComponentsBuilder.replacePath("/api/v1/messages/{id}")
                                .build(getMessageDTO.id())
                ).body(getMessageDTO));
    }

    @GetMapping("/user/{userId:\\d+}")
    public Flux<GetMessageDTO> getByUserId(final Pageable pageable, final @PathVariable Integer userId) {
        return this.messageFacade
                .getAllMessagesByUserId(userId, pageable);
    }
}

