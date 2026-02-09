package social.network.backend.reactive.exception;


import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class DtoValidationException extends RuntimeException {

    private final Errors errors;

    public DtoValidationException(Errors errors) {
        super("Validation failed");
        this.errors = errors;
    }
}