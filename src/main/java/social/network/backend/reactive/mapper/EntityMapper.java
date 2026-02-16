package social.network.backend.reactive.mapper;

public interface EntityMapper <E, D> {
    E mapToEntity(D dto);
}
