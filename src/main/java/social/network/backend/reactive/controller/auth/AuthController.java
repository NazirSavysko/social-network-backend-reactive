package social.network.backend.reactive.controller.auth;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.auth.GetLoginDTO;
import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.facade.auth.LoginFacade;
import social.network.backend.reactive.facade.auth.RegisterFacade;
import social.network.backend.reactive.validator.MonoValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public final class AuthController {

    private final RegisterFacade registerFacade;
    private final MonoValidator monoValidator;
    private final LoginFacade loginFacade;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Mono<?> login(final @RequestBody Mono<GetLoginDTO> loginDtoMono) {
        System.out.println(passwordEncoder.encode("Password123"));
        return loginDtoMono
                .transform(this.monoValidator::validate)
                .as(this.loginFacade::login);
    }

    @PostMapping(value = "/registration")
    public Mono<?> registration(final @RequestBody Mono<RegisterDTO> registerDTO) {
        return registerDTO
                .transform(this.monoValidator::validate)
                .as(this.registerFacade::register);
    }
}
