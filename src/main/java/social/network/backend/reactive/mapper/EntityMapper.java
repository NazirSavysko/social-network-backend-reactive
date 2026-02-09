package social.network.backend.reactive.mapper;

public interface EntityMapper <T, R> {
    T mapToEntity(R dto);
}
