package social.network.backend.reactive.exception_handler;


import lombok.val;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.exception.DtoValidationException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(DtoValidationException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleDtoValidationException(DtoValidationException ex) {

        val errors = ex.getErrors().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (msg1, msg2) -> msg1
                ));

        return Mono.just(ResponseEntity.badRequest().body(errors));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Mono<ResponseEntity<String>> handleBadCredentialsException(final BadCredentialsException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Mono<ResponseEntity<String>> handleUsernameNotFoundException(final UsernameNotFoundException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }

}