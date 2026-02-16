package social.network.backend.reactive.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;
import social.network.backend.reactive.repository.post.PostRepository;
import social.network.backend.reactive.service.post.PostReadService;

@Service
@RequiredArgsConstructor
public final class PostReadServiceImpl implements PostReadService {

    private final PostRepository postRepository;
    @Override
    public Flux<PostWithLikesAndImageProjection> getAllPostsByUserId(final Integer userId, final Pageable pageable) {
        return this.postRepository.findAllByUserIdWithDetails(userId, pageable);
    }
}
