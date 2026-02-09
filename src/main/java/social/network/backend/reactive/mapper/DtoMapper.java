package social.network.backend.reactive.mapper;

public interface DtoMapper<T, R> {
    R mapToDTO(T entity);
}
