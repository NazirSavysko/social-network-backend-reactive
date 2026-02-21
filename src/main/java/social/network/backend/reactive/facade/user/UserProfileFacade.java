package social.network.backend.reactive.facade.user;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.dto.user.UpdateUserDTO;

public interface UserProfileFacade {

    Mono<GetUserDTO> getUserById(Integer userId);

    Mono<GetUserDTO> updateUser(Mono<UpdateUserDTO> updateUserDTOMono);

    Mono<Void> deleteUser(Integer userId);
}
