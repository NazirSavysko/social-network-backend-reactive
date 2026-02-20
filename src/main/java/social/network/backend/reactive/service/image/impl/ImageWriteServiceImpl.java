package social.network.backend.reactive.service.image.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Image;
import social.network.backend.reactive.repository.image.ImageRepository;
import social.network.backend.reactive.service.image.ImageWriteService;

@Service
@RequiredArgsConstructor
public class ImageWriteServiceImpl implements ImageWriteService {

    private final ImageRepository imageRepository;

    @Override
    public Mono<Image> saveImage(final Image image) {
        return imageRepository.save(image);
    }
}
