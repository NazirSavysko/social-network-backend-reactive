package social.network.backend.reactive.mapper.message.impl;

import org.springframework.stereotype.Component;
import social.network.backend.reactive.dto.message.GetMessageDTO;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.mapper.message.GetMessageDTOMapper;
import social.network.backend.reactive.model.projection.MessageWithSenderAndRecipientProjection;

@Component
public final class GetMessageDTOMapperImpl implements GetMessageDTOMapper {
    @Override
    public GetMessageDTO mapToDTO(final MessageWithSenderAndRecipientProjection entity) {
        return new GetMessageDTO(
                entity.id(),
                entity.content(),
                new GetUserDTO(
                        entity.senderId(),
                        entity.senderName(),
                        entity.senderSurname(),
                        entity.senderEmail()
                ),
                new GetUserDTO(
                        entity.recipientId(),
                        entity.recipientName(),
                        entity.recipientSurname(),
                        entity.recipientEmail()
                )
        );
    }
}
