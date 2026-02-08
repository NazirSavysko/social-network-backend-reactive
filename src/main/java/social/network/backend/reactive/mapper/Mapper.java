package social.network.backend.reactive.mapper;

@FunctionalInterface
public interface Mapper<T, R> {
    R mapToDTO(T entity);
}
