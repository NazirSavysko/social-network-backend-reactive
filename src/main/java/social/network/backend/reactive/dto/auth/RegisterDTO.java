package social.network.backend.reactive.dto.auth;

import social.network.backend.reactive.annotations.ValidEmail;
import social.network.backend.reactive.annotations.ValidName;
import social.network.backend.reactive.annotations.ValidPassword;
import social.network.backend.reactive.annotations.ValidSurname;

public record RegisterDTO(
        @ValidName
        String name,
        @ValidSurname
        String surname,
        @ValidEmail
        String email,
        @ValidPassword
        String password
) {
}
