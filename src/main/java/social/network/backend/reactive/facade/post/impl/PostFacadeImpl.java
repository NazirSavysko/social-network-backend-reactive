package social.network.backend.reactive.facade.post.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.post.CreatePostDTO;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.facade.post.PostFacade;
import social.network.backend.reactive.mapper.post.GetPostWithLikeAndImageDetailsDtoMapper;
import social.network.backend.reactive.model.Image;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.service.file.FileService;
import social.network.backend.reactive.service.image.ImageService;
import social.network.backend.reactive.service.post.PostReadService;
import social.network.backend.reactive.service.post.PostWriteService;
import social.network.backend.reactive.service.user.UserReadService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public final class PostFacadeImpl implements PostFacade {

    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final UserReadService userReadService;
    private final GetPostWithLikeAndImageDetailsDtoMapper dtoMapper;
    private final ImageService imageService;
    private final FileService fileService;

    @Override
    public Flux<GetPostDTO> getAllPostsByUserId(final Integer userId, final Pageable pageable) {
        return this.postReadService
                .getAllPostsByUserId(userId, pageable)
                .flatMap(postWithLikesAndImageProjection -> {
                    return this.fileService.getContentFromFile(postWithLikesAndImageProjection.image())
                            .map(content -> new GetPostDTO(
                                    postWithLikesAndImageProjection.likesCount(),
                                    postWithLikesAndImageProjection.postText(),
                                    postWithLikesAndImageProjection.postDate(),
                                    postWithLikesAndImageProjection.id(),
                                    content
                            ));
                });

    }

    @Override
    public Mono<GetPostDTO> createPost(final Mono<CreatePostDTO> createPostDTOMono) {
        return createPostDTOMono
                .flatMap(createPostDTO -> {
                            return userReadService
                                    .getUserById(createPostDTO.userId())
                                    .flatMap(user -> {
                                                return this.fileService.writeToFile(user.getEmail(), createPostDTO.imageInFormatBase64());
                                            }
                                    ).flatMap(filePath -> {
                                                val newImage = Image.builder()
                                                        .filePath(filePath)
                                                        .build();

                                                return this.imageService.saveImage(newImage);
                                            }
                                    )
                                    .flatMap(image -> {
                                        val post = Post.builder()
                                                .userId(createPostDTO.userId())
                                                .postText(createPostDTO.postText())
                                                .postDate(Instant.now())
                                                .imageId(image.getId())
                                                .build();

                                        return this.postWriteService.savePost(post);
                                    }).map(savedPost -> {
                                        return new GetPostDTO(
                                                0,
                                                savedPost.getPostText(),
                                                savedPost.getPostDate(),
                                                savedPost.getId(),
                                                createPostDTO.imageInFormatBase64()
                                        );
                                    });
                        }
                );
    }
}
