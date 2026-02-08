package social.network.backend.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table(name = "post_like")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PostLike {

    @Id
    private Integer id;


    private Integer userId;

    private Integer postId;

}
