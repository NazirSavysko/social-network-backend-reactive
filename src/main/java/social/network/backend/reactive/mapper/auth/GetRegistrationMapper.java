package social.network.backend.reactive.mapper.auth;

import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.mapper.EntityMapper;
import social.network.backend.reactive.model.User;

public interface GetRegistrationMapper extends EntityMapper<User, RegisterDTO> {
    User mapToEntity(RegisterDTO dto);
}
