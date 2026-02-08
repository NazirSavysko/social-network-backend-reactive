package social.network.backend.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;


@Table(name = "post_comment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PostComment {

    @Id
    private Integer id;

    private Integer userId;

    private Integer postId;

    private String commentText;

    private Instant commentDate;

}
