package social.network.backend.reactive.model.projection;

import org.springframework.data.relational.core.mapping.Column;

public record MessageWithSenderAndRecipientProjection(
        Integer id,

        @Column("sender_id")
        Integer senderId,

        @Column("sender_name")
        String senderName,

        @Column("sender_surname")
        String senderSurname,

        @Column("sender_email")
        String senderEmail,

        @Column("recipient_id")
        Integer recipientId,

        @Column("recipient_name")
        String recipientName,

        @Column("recipient_surname")
        String recipientSurname,

        @Column("recipient_email")
        String recipientEmail,

        String content
) {
}
