package social.network.backend.reactive.repository.image;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import social.network.backend.reactive.model.Image;

public interface ImageRepository extends ReactiveCrudRepository<Image, Integer> {
}
