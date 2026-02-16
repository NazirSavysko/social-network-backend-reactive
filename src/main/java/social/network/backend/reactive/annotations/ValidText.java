package social.network.backend.reactive.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Size(min = 1, max = 1000, message = "{posts.create.errors.Post-text.size}")
@NotBlank(message = "{posts.create.errors.Post-text.blank}")
public @interface ValidText {

    String message() default "invalid text";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}