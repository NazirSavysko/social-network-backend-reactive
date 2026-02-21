package social.network.backend.reactive.model.projection;

import org.springframework.data.relational.core.mapping.Column;

public record SubscriptionWithSubscriberAndTargetProjection(
        Integer id,

        @Column("subscriber_id")
        Integer subscriberId,

        @Column("subscriber_name")
        String subscriberName,

        @Column("subscriber_surname")
        String subscriberSurname,

        @Column("subscriber_email")
        String subscriberEmail,

        @Column("target_id")
        Integer targetId,

        @Column("target_name")
        String targetName,

        @Column("target_surname")
        String targetSurname,

        @Column("target_email")
        String recipientEmail
) {
}
