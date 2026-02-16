package social.network.backend.reactive.mapper;

public interface DtoMapper<E, D> {
    D mapToDTO(E entity);
}
