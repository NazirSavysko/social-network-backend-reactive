package social.network.backend.reactive.facade.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.AuthResponseDTO;
import social.network.backend.reactive.dto.auth.GetLoginDTO;
import social.network.backend.reactive.facade.auth.LoginFacade;
import social.network.backend.reactive.service.auth.JwtGeneratorService;
import social.network.backend.reactive.service.user.UserReadService;

@Component
@RequiredArgsConstructor
public final class LoginFacadeImpl implements LoginFacade {

    private final UserReadService userReadService;
    private final PasswordEncoder passwordEncoder;
    private final JwtGeneratorService jwtGeneratorService;

    @Override
    public Mono<AuthResponseDTO> login(final Mono<GetLoginDTO> loginDTO) {
        return loginDTO.flatMap(dto ->
                this.userReadService.getUserByEmail(dto.email())
                        .flatMap(user -> {
                            if (this.passwordEncoder.matches(dto.password(), user.getPassword())) {
                                val token = this.jwtGeneratorService.generateToken(user);

                                return Mono.just(new AuthResponseDTO(token, user.getRole().name(),user.getId()));
                            }

                            return Mono.error(new BadCredentialsException("Wrong credentials"));
                        })
                        .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found"))));
    }
}

