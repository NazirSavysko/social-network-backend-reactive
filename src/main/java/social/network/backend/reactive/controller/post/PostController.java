package social.network.backend.reactive.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.controller.payload.UpdatePostPayload;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.dto.post.UpdatePostDTO;
import social.network.backend.reactive.facade.post.PostFacade;
import social.network.backend.reactive.validator.MonoValidator;

import java.security.Principal;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId:\\d+}")
public final class PostController {

    private final PostFacade postFacade;
    private final MonoValidator monoValidator;

    @GetMapping
    public Mono<GetPostDTO> getPost(@PathVariable Integer postId) {
        return this.postFacade.getPostById(postId);
    }

    @PutMapping("/update")
    public Mono<GetPostDTO> updatePost(final @PathVariable Integer postId,
                                       final @RequestBody Mono<UpdatePostPayload> postPayload,
                                       Mono<Principal> principalMono) {
        Mono<UpdatePostPayload> validPayloadMono = postPayload
                .transform(this.monoValidator::validate);


        return Mono.zip(validPayloadMono, principalMono)
                .map((combination) -> {
                    return new UpdatePostDTO(
                            combination.getT1().text(),
                            combination.getT1().imageInFormatBase64(),
                            combination.getT2().getName(),
                            postId
                    );
                })
                .as(this.postFacade::updatePost);
    }

    @DeleteMapping("/delete")
    public Mono<Void> deletePost(final @PathVariable Integer postId) {
        return this.postFacade.deletePost(postId);
    }

}
