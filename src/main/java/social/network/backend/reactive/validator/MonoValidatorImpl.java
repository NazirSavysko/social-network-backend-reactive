package social.network.backend.reactive.validator;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.exception.DtoValidationException;

@Component
@AllArgsConstructor
public class MonoValidatorImpl implements MonoValidator {

    private final Validator validator;

    public <T> Mono<T> validate(Mono<T> objectMono) {
        return objectMono
                .flatMap(dto -> {
                    val errors = new BeanPropertyBindingResult(dto, dto.getClass().getName());
                    validator.validate(dto, errors);
                    if (errors.hasErrors()) {
                        return Mono.error(new DtoValidationException(errors));
                    }

                    return Mono.just(dto);
                });
    }
}

