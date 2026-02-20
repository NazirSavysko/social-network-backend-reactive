package social.network.backend.reactive.facade.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.admin.GetAvgPostCount;
import social.network.backend.reactive.dto.admin.GetPostCount;
import social.network.backend.reactive.dto.admin.TimeRangeDTO;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.facade.admin.AdminFacade;
import social.network.backend.reactive.mapper.post.GetPostWithLikeAndImageDetailsDtoMapper;
import social.network.backend.reactive.mapper.user.GetUserDTOMapper;
import social.network.backend.reactive.service.admin.AdminService;

@Component
@RequiredArgsConstructor
public final class AdminFacadeImpl implements AdminFacade {

    private final AdminService adminService;
    private final GetUserDTOMapper userDTOMapper;
    private final GetPostWithLikeAndImageDetailsDtoMapper postDTOMapper;

    @Override
    public Mono<GetPostCount> getPostCount(final TimeRangeDTO timeRangeDTO) {
        return adminService
                .getPostCount(timeRangeDTO.start(), timeRangeDTO.end())
                .map(GetPostCount::new);
    }

    @Override
    public Mono<GetAvgPostCount> getAveragePostCountByDay(final TimeRangeDTO timeRangeDTO) {
        return adminService
                .getAveragePostCountByDay(timeRangeDTO.start(), timeRangeDTO.end())
                .map(GetAvgPostCount::new);
    }

    @Override
    public Flux<GetUserDTO> getTheMostActiveUser(final TimeRangeDTO timeRangeDTO) {
        return adminService.getTheMostActiveUser(timeRangeDTO.start(), timeRangeDTO.end())
                .map(this.userDTOMapper::mapToDTO);
    }

    @Override
    public Flux<GetPostDTO> getTopTenPostByLikes(final TimeRangeDTO timeRangeDTO) {
        return adminService.getTopTenPostByLikes(timeRangeDTO.start(), timeRangeDTO.end())
                .map(this.postDTOMapper::mapToDTO);
    }

    @Override
    public Flux<GetPostDTO> getTopTenPostByComments(final TimeRangeDTO timeRangeDTO) {
        return adminService.getTopTenPostByComments(timeRangeDTO.start(), timeRangeDTO.end())
                .map(this.postDTOMapper::mapToDTO);
    }
}
