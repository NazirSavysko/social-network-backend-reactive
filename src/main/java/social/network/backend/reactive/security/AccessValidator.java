package social.network.backend.reactive.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public final class AccessValidator {

    private Mono<Authentication> getAuth() {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(SecurityContext::getAuthentication);
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> Objects.requireNonNull(a.getAuthority()).equals("ROLE_ADMIN"));
    }

    public <T> Mono<T> checkOwnerOrAdmin(T targetObject, String ownerEmail) {
        return getAuth().flatMap(auth -> {
            if (isAdmin(auth) || Objects.equals(ownerEmail, auth.getName())) {
                return Mono.just(targetObject);
            }
            return Mono.error(new AccessDeniedException("Forbidden"));
        });
    }

    public <T> Mono<T> checkMessageReadAccess(T messageObject, String senderEmail, String receiverEmail) {
        return getAuth().flatMap(auth -> {
            String currentUserEmail = auth.getName();
            if (isAdmin(auth) || Objects.equals(senderEmail, currentUserEmail) || Objects.equals(receiverEmail, currentUserEmail)) {
                return Mono.just(messageObject);
            }
            return Mono.error(new AccessDeniedException("Forbidden"));
        });
    }

    public <T> Mono<T> checkSubscriptionDeleteAccess(T targetObject, String subscriberEmail, String targetEmail) {
        return getAuth().flatMap(auth -> {
            String currentUserEmail = auth.getName();

            boolean isSubscriber = java.util.Objects.equals(subscriberEmail, currentUserEmail);
            boolean isTarget = java.util.Objects.equals(targetEmail, currentUserEmail);

            if (isAdmin(auth) || isSubscriber || isTarget) {
                return reactor.core.publisher.Mono.just(targetObject);
            }

            return reactor.core.publisher.Mono.error(
                    new org.springframework.security.access.AccessDeniedException("Forbidden: You can only delete your own subscriptions or followers")
            );
        });
    }
}