package social.network.backend.reactive.facade.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.dto.user.UpdateUserDTO;
import social.network.backend.reactive.facade.user.UserProfileFacade;
import social.network.backend.reactive.mapper.user.GetUserDTOMapper;
import social.network.backend.reactive.mapper.user.UpdateUserEntityMapper;
import social.network.backend.reactive.service.user.UserReadService;
import social.network.backend.reactive.service.user.UserWriteService;
import social.network.backend.reactive.security.AccessValidator;

@Component
@RequiredArgsConstructor
public final class UserProfileFacadeImpl implements UserProfileFacade {

    private final UserReadService userReadService;
    private final UserWriteService userWriteService;
    private final UpdateUserEntityMapper entityMapper;
    private final GetUserDTOMapper dtoMapper;
    private final AccessValidator accessValidator;

    @Override
    public Mono<GetUserDTO> getUserById(final Integer userId) {
        return this.userReadService.getUserById(userId)
                .flatMap(user -> this.accessValidator.checkOwnerOrAdmin(user, user.getEmail()))
                .map(this.dtoMapper::mapToDTO);
    }

    @Override
    public Mono<GetUserDTO> updateUser(final Mono<UpdateUserDTO> updateUserDTOMono) {
        return updateUserDTOMono
                .flatMap(dto -> this.userReadService.getUserById(dto.id())
                        .flatMap(user -> this.accessValidator.checkOwnerOrAdmin(user, user.getEmail()))
                        .map(user -> dto)
                )
                .map(this.entityMapper::mapToEntity)
                .flatMap(this.userWriteService::prepareUser)
                .flatMap(this.userWriteService::updateUser)
                .map(this.dtoMapper::mapToDTO);
    }

    @Override
    public Mono<Void> deleteUser(final Integer userId) {
        return this.userReadService.getUserById(userId)
                .flatMap(user -> this.accessValidator.checkOwnerOrAdmin(user, user.getEmail()))
                .flatMap(user -> this.userWriteService.deleteUser(user.getId()));
    }
}