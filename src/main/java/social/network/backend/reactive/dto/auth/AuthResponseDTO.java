package social.network.backend.reactive.dto.auth;

public record AuthResponseDTO(
        String token,
        String role,
        Integer id
) {
}
