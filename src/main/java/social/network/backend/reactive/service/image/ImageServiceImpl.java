package social.network.backend.reactive.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Image;
import social.network.backend.reactive.repository.image.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Mono<Image> saveImage(final Image image) {
        return imageRepository.save(image);
    }
}
