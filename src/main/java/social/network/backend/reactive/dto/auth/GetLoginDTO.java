package social.network.backend.reactive.dto.auth;

import social.network.backend.reactive.annotations.ValidEmail;
import social.network.backend.reactive.annotations.ValidPassword;

public record GetLoginDTO(
        @ValidEmail
        String email,
        @ValidPassword
        String password
) {
}
