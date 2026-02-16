package social.network.backend.reactive.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.post.CreatePostDTO;
import social.network.backend.reactive.facade.post.PostFacade;
import social.network.backend.reactive.validator.MonoValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public final class PostsController {

    private final MonoValidator monoValidator;
    private final PostFacade postFacade;

    @PostMapping("/create")
    public Mono<?> createPost(final @RequestBody Mono<CreatePostDTO> payloadMono,
                              final UriComponentsBuilder uriComponentsBuilder) {

        return payloadMono
                .transform(this.monoValidator::validate)
                .as(this.postFacade::createPost);
    }

    @GetMapping("/user/{userId}")
    public Flux<?> getAllPostsByUser(final @PathVariable Integer userId,final Pageable pageable) {

        return this.postFacade
                .getAllPostsByUserId(userId, pageable);
    }

}
