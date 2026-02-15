package social.network.backend.reactive.facade.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.dto.user.UpdateUserDTO;
import social.network.backend.reactive.facade.user.UserProfileFacade;
import social.network.backend.reactive.mapper.user.GetUserDTOMapper;
import social.network.backend.reactive.mapper.user.UpdateUserEntityMapper;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.service.user.UserReadService;
import social.network.backend.reactive.service.user.UserWriteService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public final class UserProfileFacadeImpl implements UserProfileFacade {

    private final UserReadService userReadService;
    private final UserWriteService userWriteService;
    private final UpdateUserEntityMapper entityMapper;
    private final GetUserDTOMapper dtoMapper;

    @Override
    public Mono<GetUserDTO> getUserById(final Integer userId) {
        return getMonoUserByIdWithSecurityCheck(userId)
                .map(dtoMapper::mapToDTO);
    }

    @Override
    public Mono<GetUserDTO> updateUser(final Mono<UpdateUserDTO> updateUserDTOMono) {
        return updateUserDTOMono
                .map(this.entityMapper::mapToEntity)
                .flatMap(this.userWriteService::prepareUser)
                .flatMap(this.userWriteService::updateUser)
                .map(dtoMapper::mapToDTO);
    }

    @Override
    public Mono<Void> deleteUser(final GetUserDTO getUserDTO) {
        return this.userWriteService.deleteUser(getUserDTO.id());
    }


    private Mono<User> getMonoUserByIdWithSecurityCheck(final Integer userId) {
        return userReadService.getUserById(userId)
                .filterWhen(this::checkAccess);
    }

    private Mono<Boolean> checkAccess(User targetUser) {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(SecurityContext::getAuthentication)
                .map(auth -> isOwnerOrAdmin(auth, targetUser))
                .defaultIfEmpty(false);
    }

    private boolean isOwnerOrAdmin(Authentication auth, User targetUser) {
        val currentUserEmail = auth.getName();

        val isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));

        val isOwner = targetUser.getEmail().equals(currentUserEmail);

        return isAdmin || isOwner;
    }
}

