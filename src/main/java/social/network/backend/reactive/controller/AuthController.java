package social.network.backend.reactive.controller;


import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.GetLoginDTO;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping("/api/v1/auth")
public final class AuthController {

    @PostMapping("/login")
    public Mono<String> login(@Valid @RequestBody Mono<GetLoginDTO> loginDtoMono) {
        return loginDtoMono
                .map(dto -> {
                    return "Login successful";
                });
    }
}
