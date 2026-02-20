package social.network.backend.reactive.controller.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.facade.image.ImageFacade;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public final class ImageController {

    private final ImageFacade imageFacade;

    @GetMapping("/{email:.+}/{filename:.+}")
    public Mono<ResponseEntity<Resource>> serveImage(final @PathVariable String email,final @PathVariable String filename) {

        return this.imageFacade.getImageResourceByPath(email, filename)
                .map(resource ->
                        ok().body(resource)
                );
    }

}
