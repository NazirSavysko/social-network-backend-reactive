package social.network.backend.reactive.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.admin.GetAvgPostCount;
import social.network.backend.reactive.dto.admin.GetPostCount;
import social.network.backend.reactive.dto.admin.TimeRangeDTO;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.facade.admin.AdminFacade;

@RestController
@RequestMapping("/api/v1/admins/statistics")
@RequiredArgsConstructor
public final class AdminController {

    private final AdminFacade adminFacade;

    @PostMapping("/posts/count")
    public Mono<GetPostCount> getPostCount(final @RequestBody Mono<TimeRangeDTO> timeRangeDTO) {

        return timeRangeDTO.flatMap(this.adminFacade::getPostCount);
    }

    @PostMapping("/posts/count/average-per-day")
    public Mono<GetAvgPostCount> getAveragePostCountByDay(final @RequestBody  Mono<TimeRangeDTO> timeRangeDTO) {
        return timeRangeDTO.flatMap(this.adminFacade::getAveragePostCountByDay);
    }

    @PostMapping("/users/most-active")
    public Flux<GetUserDTO> getTenTheMostActiveUsers(final @RequestBody  Mono<TimeRangeDTO> timeRangeDTO) {
        return timeRangeDTO.flatMapMany(this.adminFacade::getTheMostActiveUser);
    }

    @PostMapping("/posts/top10/likes")
    public Flux<GetPostDTO> getTopTenPostsByLikes(final @RequestBody  Mono<TimeRangeDTO> timeRangeDTO) {
        return timeRangeDTO.flatMapMany(this.adminFacade::getTopTenPostByLikes);
    }

    @PostMapping("/posts/top10/comments")
    public Flux<GetPostDTO>  getTopTenPostsByComments(final @RequestBody  Mono<TimeRangeDTO> timeRangeDTO) {
        return timeRangeDTO.flatMapMany(this.adminFacade::getTopTenPostByComments);
    }
}