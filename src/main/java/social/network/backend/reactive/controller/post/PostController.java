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

    @ModelAttribute("post")
    public Mono<GetPostDTO> getPostId(@PathVariable Integer postId) {
        return this.postFacade.getPostById(postId);
    }

    @GetMapping
    public Mono<GetPostDTO> getPost(final @ModelAttribute(value = "post") Mono<GetPostDTO> post) {
        return post;
    }

    @PutMapping("/update")
    public Mono<GetPostDTO> updatePost(final @ModelAttribute(value = "post") Mono<GetPostDTO> post,
                                       final @RequestBody Mono<UpdatePostPayload> postPayload) {
        return postPayload
                .transform(this.monoValidator::validate)
                .zipWith(post, (updatePostPayload, postDTO) -> new UpdatePostDTO(
                        updatePostPayload.text(),
                        updatePostPayload.imageInFormatBase64(),
                        postDTO.id()
                ))
                .as(this.postFacade::updatePost);
    }

    @DeleteMapping("/delete")
    public Mono<Void> deletePost(final @ModelAttribute(value = "post") Mono<GetPostDTO> post) {
        return this.postFacade.deletePost(post);
    }

}
