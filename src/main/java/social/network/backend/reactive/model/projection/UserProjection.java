package social.network.backend.reactive.model.projection;

public record UserProjection(
        Integer id,
        String name,
        String surname,
        String email
) {
}
