package social.network.backend.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;


@Table(name = "post")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Post {

    @Id
    private Integer id;

    private String postText;

    private Instant postDate;

    private Integer imageId;

    private Integer userId;
}
