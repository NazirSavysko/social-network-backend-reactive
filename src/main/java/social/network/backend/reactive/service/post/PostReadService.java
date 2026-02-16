package social.network.backend.reactive.service.post;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

public interface PostReadService {

    Flux<PostWithLikesAndImageProjection> getAllPostsByUserId(Integer userId, Pageable pageable);
}
