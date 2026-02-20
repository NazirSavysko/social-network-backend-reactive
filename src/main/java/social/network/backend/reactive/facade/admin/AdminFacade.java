package social.network.backend.reactive.facade.admin;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.admin.GetAvgPostCount;
import social.network.backend.reactive.dto.admin.GetPostCount;
import social.network.backend.reactive.dto.admin.TimeRangeDTO;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.dto.user.GetUserDTO;

public interface AdminFacade {
    Mono<GetPostCount> getPostCount(TimeRangeDTO timeRangeDTOMono);

    Mono<GetAvgPostCount> getAveragePostCountByDay(TimeRangeDTO timeRangeDTO);

    Flux<GetUserDTO> getTheMostActiveUser(TimeRangeDTO timeRangeDTO);

    Flux<GetPostDTO> getTopTenPostByLikes(TimeRangeDTO timeRangeDTO);

    Flux<GetPostDTO> getTopTenPostByComments(TimeRangeDTO timeRangeDTO);
}
