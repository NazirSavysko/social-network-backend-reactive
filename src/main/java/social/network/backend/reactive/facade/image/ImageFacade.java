package social.network.backend.reactive.facade.image;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

public interface ImageFacade {
    Mono<Resource> getImageResourceByPath(String email, String filename);
}
