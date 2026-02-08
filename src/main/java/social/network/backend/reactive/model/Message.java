package social.network.backend.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table(name = "message")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Message {
    @Id
    private Integer id;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Integer senderId;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private Integer recipientId;

    private LocalDateTime messageDate;

    private String messageText;
}
