package social.network.backend.reactive.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.GetLoginDTO;
import social.network.backend.reactive.dto.auth.RegisterDTO;
import social.network.backend.reactive.facade.auth.RegisterFacade;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public final class AuthController {

    private final RegisterFacade registerFacade;
//    private final LoginFacade loginFacade;

    @PostMapping("/login")
    public Mono<String> login(@Valid @RequestBody Mono<GetLoginDTO> loginDtoMono) {
        return loginDtoMono
                .map(dto -> {
                    return "Login successful";
                });
    }

    @PostMapping(value = "/registration",produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<?> registration(final @Valid @RequestBody RegisterDTO registerDTO) {

        return registerFacade.register(registerDTO);
    }
}
