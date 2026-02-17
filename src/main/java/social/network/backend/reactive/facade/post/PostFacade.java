package social.network.backend.reactive.facade.post;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.controller.payload.UpdatePostPayload;
import social.network.backend.reactive.dto.post.CreatePostDTO;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.dto.post.UpdatePostDTO;

public interface PostFacade {

    @Transactional(readOnly = true)
    Flux<GetPostDTO> getAllPostsByUserId(Integer userId, Pageable pageable);

    Mono<GetPostDTO> createPost(Mono<CreatePostDTO> createPostDTOMono);

    Mono<GetPostDTO> getPostById(Integer postId);

    Mono<Void> deletePost(Mono<GetPostDTO> post);

    Mono<GetPostDTO> updatePost(Mono<UpdatePostDTO> updatePostPayload);
}
