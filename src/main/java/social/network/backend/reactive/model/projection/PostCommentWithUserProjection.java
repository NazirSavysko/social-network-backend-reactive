package social.network.backend.reactive.model.projection;

public record PostCommentWithUserProjection(
        Integer id,
        String commentText,
        String userName,
        String userSurname,
        String userEmail,
        Integer userId

) {
}
