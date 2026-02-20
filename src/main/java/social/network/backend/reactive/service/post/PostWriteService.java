package social.network.backend.reactive.service.post;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

import java.time.Instant;

public interface PostWriteService {
    Mono<Post> savePost(Post post);

    Mono<PostWithLikesAndImageProjection> updatePost(Integer id, String content, Instant data, Integer imageId);
}
