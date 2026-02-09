package social.network.backend.reactive.exception_handler;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.exception.DtoValidationException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DtoValidationException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleDtoValidationException(DtoValidationException ex) {

        // Берем ошибки из нашего кастомного исключения
        Map<String, String> errors = ex.getErrors().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (msg1, msg2) -> msg1
                ));

        return Mono.just(ResponseEntity.badRequest().body(errors));
    }
}