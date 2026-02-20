package social.network.backend.reactive.dto.message;

import social.network.backend.reactive.dto.user.GetUserDTO;

public record GetMessageDTO(
        Integer id,
        String content,
        GetUserDTO sender,
        GetUserDTO receiver
){
}
