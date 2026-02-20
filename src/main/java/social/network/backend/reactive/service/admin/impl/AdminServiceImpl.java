package social.network.backend.reactive.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.model.User;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;
import social.network.backend.reactive.repository.post.PostAnalyticRepository;
import social.network.backend.reactive.repository.user.UserAnalyticsRepository;
import social.network.backend.reactive.service.admin.AdminService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public final class AdminServiceImpl implements AdminService {

    private final int LIMIT = 10;

    private final UserAnalyticsRepository userAnalyticsRepository;
    private final PostAnalyticRepository postAnalyticsRepository;

    @Override
    public Mono<Integer> getPostCount(final Instant start, final Instant end) {
        return this.postAnalyticsRepository.countPostsByPeriod(start, end) ;
    }

    @Override
    public Mono<Double> getAveragePostCountByDay(final Instant start, final Instant end) {
        return this.postAnalyticsRepository.calculateAveragePostsPerDay(start, end);
    }

    @Override
    public Flux<User> getTheMostActiveUser(final Instant start, final Instant end) {
        return this.userAnalyticsRepository.findTopUsersByAvgPostsPerActiveDay(LIMIT,start, end)
                .switchIfEmpty(Mono.error(new RuntimeException("No active users found in the given period")));
    }

    @Override
    public Flux<PostWithLikesAndImageProjection> getTopTenPostByLikes(final Instant start, final Instant end) {
        return this.postAnalyticsRepository.findTopTenMostLikedPostsByPeriod(LIMIT, start, end)
                .switchIfEmpty(Mono.error(new RuntimeException("No posts found in the given period")));
    }

    @Override
    public Flux<PostWithLikesAndImageProjection> getTopTenPostByComments(final Instant start, final Instant end) {
        return this.postAnalyticsRepository.findTopTenMostCommentedPostsByPeriod(LIMIT, start, end)
                .switchIfEmpty(Mono.error(new RuntimeException("No posts found in the given period")));
    }
}
