package social.network.backend.reactive.facade.user;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.user.GetUserDTO;

public interface UserProfileFacade {

    Mono<GetUserDTO> getUserById(Integer userId);
}
