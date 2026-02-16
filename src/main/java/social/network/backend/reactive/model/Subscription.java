package social.network.backend.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;


@Table(name = "subscription", schema = "social_network")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Subscription {

    @Id

    private Integer id;

    private Integer subscriberId;

    private Integer targetId;

    private Instant subscribedAt;
}
