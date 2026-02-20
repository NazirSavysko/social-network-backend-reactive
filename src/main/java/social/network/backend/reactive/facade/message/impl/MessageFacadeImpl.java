package social.network.backend.reactive.facade.message.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.message.CreateMessageDTO;
import social.network.backend.reactive.dto.message.GetMessageDTO;
import social.network.backend.reactive.dto.message.UpdateMessageDTO;
import social.network.backend.reactive.facade.message.MessageFacade;
import social.network.backend.reactive.mapper.message.GetMessageDTOMapper;
import social.network.backend.reactive.mapper.user.GetUserDTOMapper;
import social.network.backend.reactive.model.Message;
import social.network.backend.reactive.service.message.MessageReadService;
import social.network.backend.reactive.service.message.MessageWriteService;
import social.network.backend.reactive.service.user.UserReadService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public final class MessageFacadeImpl implements MessageFacade {

    private final GetMessageDTOMapper getMessageDTOMapper;
    private final MessageWriteService messageWriteService;
    private final MessageReadService messageReadService;
    private final UserReadService userReadService;
    private final GetUserDTOMapper getUserDTOMapper;

    @Override
    public Flux<GetMessageDTO> getAllMessagesByUserId(final Integer userId, final Pageable pageable) {
        return this.messageReadService
                .getAllMessagesByUserId(userId, pageable)
                .map(this.getMessageDTOMapper::mapToDTO);
    }

    @Override
    public Mono<GetMessageDTO> createMessage(final Mono<CreateMessageDTO> createMessageMono) {
        return createMessageMono.flatMap(dto ->

                Mono.zip(
                        this.userReadService.getUserById(dto.senderId()),
                        this.userReadService.getUserById(dto.receiverId())
                ).flatMap(tuple -> {
                    val sender = tuple.getT1();
                    val recipient = tuple.getT2();

                    val message = Message.builder()
                            .senderId(sender.getId())
                            .messageText(dto.content())
                            .messageDate(LocalDateTime.now())
                            .recipientId(recipient.getId())
                            .build();

                    return this.messageWriteService
                            .saveMessage(message)
                            .map(savedMessage -> new GetMessageDTO(
                                    savedMessage.getId(),
                                    savedMessage.getMessageText(),
                                    this.getUserDTOMapper.mapToDTO(sender),
                                    this.getUserDTOMapper.mapToDTO(recipient)
                            ));
                })
        );
    }

    @Override
    public Mono<GetMessageDTO> getMessageById(final Integer messageId) {
        return this.messageReadService
                .getMessageWithDetailsById(messageId)
                .map(this.getMessageDTOMapper::mapToDTO);
    }

    @Override
    public Mono<GetMessageDTO> updateMessage(final Mono<UpdateMessageDTO> updateMessage) {
        return updateMessage
                .flatMap(updateMessageDTO -> this.messageWriteService
                        .updateMessage(updateMessageDTO.id(), updateMessageDTO.content())
                )
                .map(this.getMessageDTOMapper::mapToDTO);
    }

    @Override
    public Mono<Void> deleteMessage(final Integer messageId) {
        return this.messageWriteService.deleteMessageById(messageId);
    }
}
