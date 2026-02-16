package social.network.backend.reactive.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.repository.post.PostRepository;
import social.network.backend.reactive.service.post.PostWriteService;
@Service
@RequiredArgsConstructor
public final class PostWriteServiceImpl implements PostWriteService {

    private final PostRepository postRepository;
    @Override
    public Mono<Post> savePost(final Post post) {
        return postRepository.save(post);
    }
}
