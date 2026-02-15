package social.network.backend.reactive.mapper.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.mapper.user.GetUserDTOMapper;
import social.network.backend.reactive.model.User;

@Component
@RequiredArgsConstructor
public final class GetUserDTOMapperImpl implements GetUserDTOMapper {
    @Override
    public GetUserDTO mapToDTO(final User entity) {
        return new GetUserDTO(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getEmail()
        );
    }
}
