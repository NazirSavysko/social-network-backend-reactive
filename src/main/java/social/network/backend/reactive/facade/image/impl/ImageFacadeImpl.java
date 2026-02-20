package social.network.backend.reactive.facade.image.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.facade.image.ImageFacade;
import social.network.backend.reactive.service.file.FileService;

@Component
@RequiredArgsConstructor
public class ImageFacadeImpl implements ImageFacade {

    private final FileService fileService;

    @Override
    public Mono<Resource> getImageResourceByPath(final String email, final String filename) {
        val filePath = email + "/" + filename;

        return this.fileService.getFileAsResource(filePath);
    }
}
