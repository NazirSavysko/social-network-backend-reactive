package social.network.backend.reactive.dto.user;

public record UpdateUserDTO(
        Integer id,
        String name,
        String surname,
        String email
) {
}