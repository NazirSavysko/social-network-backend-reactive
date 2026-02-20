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
import social.network.backend.reactive.model.Image;
import social.network.backend.reactive.model.Post;
import social.network.backend.reactive.model.projection.PostWithLikesAndImageProjection;
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
    private final ImageService imageService;
    private final FileService fileService;

    @Override
    public Flux<GetPostDTO> getAllPostsByUserId(final Integer userId, final Pageable pageable) {
        return this.postReadService
                .getAllPostsByUserId(userId, pageable)
                .flatMap(this::convertIntoGetPostDTO);

    }

    @Override
    public Mono<GetPostDTO> createPost(final Mono<CreatePostDTO> createPostDTOMono) {
        return createPostDTOMono
                .flatMap(createPostDTO -> userReadService
                        .getUserById(createPostDTO.userId())
                        .flatMap(user -> this.saveImage(user.getEmail(), createPostDTO.imageInFormatBase64()))
                        .flatMap(image -> {
                            val post = Post.builder()
                                    .userId(createPostDTO.userId())
                                    .postText(createPostDTO.postText())
                                    .postDate(Instant.now())
                                    .imageId(image.getId())
                                    .build();

                            return this.postWriteService.savePost(post);
                        }).map(savedPost -> new GetPostDTO(
                                0,
                                savedPost.getPostText(),
                                savedPost.getPostDate(),
                                savedPost.getId(),
                                createPostDTO.imageInFormatBase64()
                        ))
                );
    }

    @Override
    public Mono<GetPostDTO> getPostById(final Integer postId) {
        return this.postReadService
                .getPostById(postId)
                .flatMap(this::convertIntoGetPostDTO);
    }

    @Override
    public Mono<Void> deletePost(final Integer postId) {
        return null;
    }

    @Override
    public Mono<GetPostDTO> updatePost(final Mono<UpdatePostDTO> updatePostPayload) {
        return updatePostPayload
                .flatMap(updatePostDTO -> postReadService
                        .getPostById(updatePostDTO.id())
                        .flatMap(post -> this.saveImage(updatePostDTO.userEmail(), updatePostDTO.imageInFormatBase64())
                                .flatMap(savedImage -> this.postWriteService.updatePost(
                                        post.id(),
                                        post.postText(),
                                        Instant.now(),
                                        savedImage.getId()
                                )).map(
                                        updatedPost -> new GetPostDTO(
                                                updatedPost.likesCount(),
                                                updatedPost.postText(),
                                                updatedPost.postDate(),
                                                updatedPost.id(),
                                                updatePostDTO.imageInFormatBase64()
                                        )
                                )));
    }

    private Mono<Image> saveImage(final String directory, final String imageInFormatBase64) {
        return this.fileService.writeToFile(directory, imageInFormatBase64)
                .flatMap(filePath -> {
                    val image = Image.builder()
                            .filePath(filePath)
                            .build();

                    return this.imageService.saveImage(image);
                });

    }

    private Mono<? extends GetPostDTO> convertIntoGetPostDTO(final PostWithLikesAndImageProjection postWithLikesAndImageProjection) {
        return this.fileService.getContentFromFile(postWithLikesAndImageProjection.image())
                .map(content -> new GetPostDTO(
                        postWithLikesAndImageProjection.likesCount(),
                        postWithLikesAndImageProjection.postText(),
                        postWithLikesAndImageProjection.postDate(),
                        postWithLikesAndImageProjection.id(),
                        content)
                );
    }
}
