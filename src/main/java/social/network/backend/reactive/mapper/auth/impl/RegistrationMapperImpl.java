package social.network.backend.reactive.mapper.auth.impl;

import org.springframework.stereotype.Component;
import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.mapper.auth.GetRegistrationMapper;
import social.network.backend.reactive.model.User;

import static social.network.backend.reactive.model.User.builder;

@Component
public final class RegistrationMapperImpl implements GetRegistrationMapper {

    @Override
    public User mapToEntity(final RegisterDTO dto) {
        return builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .surname(dto.surname())
                .build();
    }
}
