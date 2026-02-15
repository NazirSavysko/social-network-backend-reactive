package social.network.backend.reactive.mapper.user.impl;

import org.springframework.stereotype.Component;
import social.network.backend.reactive.dto.user.UpdateUserDTO;
import social.network.backend.reactive.mapper.user.UpdateUserEntityMapper;
import social.network.backend.reactive.model.User;

@Component
public final class UpdateUserEntityMapperImpl implements UpdateUserEntityMapper {

    @Override
    public User mapToEntity(final UpdateUserDTO dto) {
        return User.builder()
                .id(dto.id())
                .name(dto.name())
                .surname(dto.surname())
                .email(dto.email())
                .build();
    }
}
