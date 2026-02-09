package social.network.backend.reactive.validator;

import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

public interface MonoValidator {
    <T> Mono<T> validate(Mono<T> objectMono);
}
