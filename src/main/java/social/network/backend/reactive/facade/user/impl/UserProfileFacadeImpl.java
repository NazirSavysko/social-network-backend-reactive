package social.network.backend.reactive.facade.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.facade.user.UserProfileFacade;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.service.user.UserReadService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public final class UserProfileFacadeImpl implements UserProfileFacade {

    private final UserReadService userReadService;
    private final PostReadService postReadService;
    private final MessageReadService messageReadService;

    @Override
    public Mono<GetUserDTO> getUserById(final Integer userId) {
       return getMonoUserById(userId)
               .zipWhen()
    }

    private Mono<User> getMonoUserById(final Integer userId) {
        return userReadService.getUserById(userId)
                .filter(targetUser -> {
                    final Mono<Boolean> roleAdmin = ReactiveSecurityContextHolder.getContext()
                            .mapNotNull(SecurityContext::getAuthentication)
                            .map(auth -> {
                                        val currentUserEmail = auth.getName();

                                        val isAdmin = auth.getAuthorities().stream()
                                                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));

                                        val isOwner = targetUser.getEmail().equals(currentUserEmail);

                                        return isAdmin || isOwner;
                                    }
                            );
                });
    }

}
