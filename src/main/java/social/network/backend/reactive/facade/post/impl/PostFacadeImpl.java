package social.network.backend.reactive.facade.post.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.post.CreatePostDTO;
import social.network.backend.reactive.dto.post.GetPostDTO;
import social.network.backend.reactive.dto.post.UpdatePostDTO;
import social.network.backend.reactive.facade.post.PostFacade;
import social.network.backend.reactive.mapper.post.GetPostWithLikeAndImageDetailsDtoMapper;
import social.network.backend.reactive.model.Image;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.service.file.FileService;
import social.network.backend.reactive.service.image.ImageReadService;
import social.network.backend.reactive.service.image.ImageWriteService;
import social.network.backend.reactive.service.post.PostReadService;
import social.network.backend.reactive.service.post.PostWriteService;
import social.network.backend.reactive.service.user.UserReadService;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public final class PostFacadeImpl implements PostFacade {

    private final GetPostWithLikeAndImageDetailsDtoMapper dtoMapper;
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final UserReadService userReadService;
    private final ImageWriteService imageWriteService;
    private final ImageReadService imageReadService;
    private final FileService fileService;

    @Override
    public Flux<GetPostDTO> getAllPostsByUserId(final Integer userId, final Pageable pageable) {
        return this.postReadService
                .getAllPostsByUserId(userId, pageable)
                .map(this.dtoMapper::mapToDTO);
    }

    @Override
    public Mono<GetPostDTO> createPost(final Mono<CreatePostDTO> createPostDTOMono) {
        return createPostDTOMono
                .flatMap(createPostDTO -> userReadService
                        .getUserById(createPostDTO.userId())
                        .flatMap(user -> this.saveImage(user.getEmail(), createPostDTO.imageInFormatBase64()))
                        .zipWhen(image -> {
                            val post = Post.builder()
                                    .userId(createPostDTO.userId())
                                    .postText(createPostDTO.postText())
                                    .postDate(Instant.now())
                                    .imageId(image.getId())
                                    .build();

                            return this.postWriteService.savePost(post);
                        }, (image, post) -> new GetPostDTO(
                                0,
                                post.getPostText(),
                                post.getPostDate(),
                                post.getId(),
                                image.getFilePath()
                        ))
                );
    }

    @Override
    public Mono<GetPostDTO> getPostById(final Integer postId) {
        return this.postReadService
                .getPostById(postId)
                .map(this.dtoMapper::mapToDTO);
    }

    @Override
    public Mono<Void> deletePost(final Integer postId) {
        return this.postWriteService
                .deletePost(postId)
                .then(Mono.defer(() -> this.imageReadService.getImageById(postId)
                        .flatMap(image -> this.fileService.deleteFile(image.getFilePath()))));
    }

    @Override
    public Mono<GetPostDTO> updatePost(final Mono<UpdatePostDTO> updatePostPayload) {
        return updatePostPayload
                .flatMap(updatePostDTO -> this.postReadService
                        .getPostById(updatePostDTO.id())
                        .flatMap(post -> this.saveImage(updatePostDTO.userEmail(), updatePostDTO.imageInFormatBase64())
                                .flatMap(savedImage -> this.postWriteService.updatePost(
                                                post.id(),
                                                updatePostDTO.text(),
                                                Instant.now(),
                                                savedImage.getId()
                                        )
                                ).map(this.dtoMapper::mapToDTO)
                        )
                );
    }

    private Mono<Image> saveImage(final String directory, final String imageInFormatBase64) {
        return this.fileService.writeToFile(directory, imageInFormatBase64)
                .flatMap(filePath -> {
                    val image = Image.builder()
                            .filePath(filePath)
                            .build();

                    return this.imageWriteService.saveImage(image);
                });

    }
}
