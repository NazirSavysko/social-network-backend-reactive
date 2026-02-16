package social.network.backend.reactive.service.post;

import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Post;

public interface PostWriteService {
    Mono<Post> savePost(Post post);
}
