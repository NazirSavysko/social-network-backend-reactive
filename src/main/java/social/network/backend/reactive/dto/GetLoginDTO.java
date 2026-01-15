package social.network.backend.reactive.dto;

public record GetLoginDTO(
        String email,
        String password
) {
}
