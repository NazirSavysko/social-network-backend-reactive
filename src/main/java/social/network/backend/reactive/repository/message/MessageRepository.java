package social.network.backend.reactive.repository.message;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Message;
import social.network.backend.reactive.model.projection.MessageWithSenderAndRecipientProjection;

public interface MessageRepository extends ReactiveCrudRepository<Message, Integer> {

    @Modifying
    @Query("DELETE FROM social_network.subscription WHERE id = :id")
    Mono<Integer> deleteSubscriptionById(Integer id);

    @Query("""
            SELECT
                m.id AS id,
                sender.id AS sender_id,
                sender.name AS sender_name,
                sender.surname AS sender_surname,
                sender.email AS sender_email,
                recipient.id AS recipient_id,
                recipient.name AS recipient_name,
                recipient.surname AS recipient_surname,
                recipient.email AS recipient_email,
                m.message_text AS content
            FROM social_network.message m
            JOIN social_network.social_user sender ON m.sender_id = sender.id
            JOIN social_network.social_user recipient ON m.receiver_id = recipient.id
            WHERE m.id = :messageId
            """)
    Mono<MessageWithSenderAndRecipientProjection> findMessageWithSenderAndRecipientById(Integer messageId);

    @Modifying
    @Query("""
            UPDATE social_network.message
                   SET message_text = :content
            WHERE id = :id
            """)
    Mono<Integer> updateMessageContent(Integer id, String content);

    @Query("""
            SELECT
                m.id AS id,
                sender.id AS sender_id,
                sender.name AS sender_name,
                sender.surname AS sender_surname,
                sender.email AS sender_email,
                recipient.id AS recipient_id,
                recipient.name AS recipient_name,
                recipient.surname AS recipient_surname,
                recipient.email AS recipient_email,
                m.message_text AS content
            FROM social_network.message m
            JOIN social_network.social_user sender ON m.sender_id = sender.id
            JOIN social_network.social_user recipient ON m.receiver_id = recipient.id
            WHERE m.sender_id = :userId OR m.receiver_id = :userId
            ORDER BY m.id DESC
            LIMIT :pageSize
            OFFSET :offset
            """)
    Flux<MessageWithSenderAndRecipientProjection> findAllMessagesByUserIdWithDetails(Integer userId, int pageSize, long offset);
}
