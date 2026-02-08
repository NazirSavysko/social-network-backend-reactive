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

//    @OneToOne(cascade = ALL)
//    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Integer imageId;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Integer userId;

//    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = ALL)
//    private List<PostLike> postLikes;
//
//    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = ALL)
//    private List<PostComment> postComments;
}
