package social.network.backend.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table(name = "message", schema = "social_network")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Message {
    @Id
    private Integer id;

    private Integer senderId;

    private Integer recipientId;

    private LocalDateTime messageDate;

    private String messageText;
}
