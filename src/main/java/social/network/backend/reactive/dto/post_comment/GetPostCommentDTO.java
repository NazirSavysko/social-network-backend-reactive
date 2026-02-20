package social.network.backend.reactive.dto.post_comment;

import social.network.backend.reactive.dto.user.GetUserDTO;

public record GetPostCommentDTO(
        Integer id,
        String commentText,
        GetUserDTO user
) {
}
