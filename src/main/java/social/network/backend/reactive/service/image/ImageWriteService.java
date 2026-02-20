package social.network.backend.reactive.service.image;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Image;

public interface ImageWriteService {
    Mono<Image> saveImage(Image image);
}
