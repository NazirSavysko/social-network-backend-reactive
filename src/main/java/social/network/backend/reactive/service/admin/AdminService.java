package social.network.backend.reactive.service.admin;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.admin.GetPostCount;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;

import java.time.Instant;

public interface AdminService {
    Mono<Integer> getPostCount(Instant start, Instant end);

   Mono<Double> getAveragePostCountByDay(Instant start, Instant end);

    Flux<User> getTheMostActiveUser(Instant start, Instant end);

    Flux<PostWithLikesAndImageProjection> getTopTenPostByLikes(Instant start, Instant end);

    Flux<PostWithLikesAndImageProjection>  getTopTenPostByComments(Instant start, Instant end);
}
