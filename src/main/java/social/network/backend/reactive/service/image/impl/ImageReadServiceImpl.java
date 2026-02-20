package social.network.backend.reactive.service.image.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Image;
import social.network.backend.reactive.repository.image.ImageRepository;
import social.network.backend.reactive.service.image.ImageReadService;

@Service
@RequiredArgsConstructor
public final class ImageReadServiceImpl implements ImageReadService {

    private final ImageRepository imageRepository;
    @Override
    public Mono<Image> getImageById(final Integer id) {
        return this.imageRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Image not found")));
    }
}
