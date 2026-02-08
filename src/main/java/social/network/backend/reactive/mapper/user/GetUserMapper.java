package social.network.backend.reactive.mapper.user;

import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.mapper.Mapper;
import social.network.backend.reactive.model.User;

public class GetUserMapper implements Mapper<User, GetUserDTO> {
    @Override
    public GetUserDTO mapToDTO(final User entity) {
        return null;
    }
}
