package social.network.backend.reactive.controller;


import jakarta.validation.Valid;
import org.springframework.boot.webflux.error.DefaultErrorAttributes;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.joining;

@RestController("/api/v1/auth")
public final class AuthController {

    @PostMapping("/login")
    private Mono<?> login(@Valid @RequestBody GetLoginDTO getLoginDTO, final BindingResult result) {
        if (result.hasErrors()) {
            return Mono.error(new IllegalArgumentException(result.getAllErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage
            ).collect(joining(", \n"))));
        }

        return Mono.just("Login successful");
    }
}
