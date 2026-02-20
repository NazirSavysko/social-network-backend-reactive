package social.network.backend.reactive.mapper.message;

import social.network.backend.reactive.dto.message.GetMessageDTO;
import social.network.backend.reactive.mapper.DtoMapper;
import social.network.backend.reactive.model.projection.MessageWithSenderAndRecipientProjection;

public interface GetMessageDTOMapper extends DtoMapper<MessageWithSenderAndRecipientProjection,GetMessageDTO> {
}
