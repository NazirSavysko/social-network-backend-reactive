package social.network.backend.reactive.repository.subscription;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.model.Subscription;
import social.network.backend.reactive.model.projection.SubscriptionWithSubscriberAndTargetProjection;
import social.network.backend.reactive.model.projection.UserProjection;

public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Integer> {

    @Modifying
    @Query("DELETE FROM social_network.subscription WHERE id = :id")
    Mono<Integer> deleteSubscriptionById(Integer id);

    @Query("""
            SELECT
                s.id AS id,
                sub.id AS subscriber_id,
                sub.name AS subscriber_name,
                sub.surname AS subscriber_surname,
                sub.email AS subscriber_email,
                tar.id AS target_id,
                tar.name AS target_name,
                tar.surname AS target_surname,
                tar.email AS target_email
            FROM social_network.subscription s
            JOIN social_network.social_user sub ON s.subscriber_id = sub.id
            JOIN social_network.social_user tar ON s.target_id = tar.id
            WHERE s.id = :subscriptionId
            """)
    Mono<SubscriptionWithSubscriberAndTargetProjection> findSubscriptionWithSubscriberAndTargetById(Integer subscriptionId);

    @Query("""
            SELECT
                s.id AS id,
                sub.id AS subscriber_id,
                sub.name AS subscriber_name,
                sub.surname AS subscriber_surname,
                sub.email AS subscriber_email,
                tar.id AS target_id,
                tar.name AS target_name,
                tar.surname AS target_surname,
                tar.email AS target_email
            FROM social_network.subscription s
            JOIN social_network.social_user sub ON s.subscriber_id = sub.id
            JOIN social_network.social_user tar ON s.target_id = tar.id
            WHERE s.subscriber_id = :userId
            LIMIT :pageSize
            OFFSET :offset
            """)
    Flux<SubscriptionWithSubscriberAndTargetProjection> findSubscriptionsWithSubscriberAndTargetBySubscriberId(Integer userId, int pageSize, long offset);

    @Query("SELECT COUNT(*) FROM social_network.subscription WHERE subscriber_id = :subscriberId")
    Mono<Integer> countSubscriptionsBySubscriberId(Integer subscriberId);

    @Query("SELECT COUNT(*) FROM social_network.subscription WHERE target_id = :targetId")
    Mono<Integer> countSubscriptionsByTargetId(Integer targetId);

    @Modifying
    @Query("""
            UPDATE social_network.subscription
            SET subscriber_id = :subscriberId,
                target_id = :targetId
            WHERE id = :id
            """)
    Mono<Integer> updateSubscription(Integer id, Integer subscriberId, Integer targetId);

    @Query("""
            SELECT
                u.id AS id,
                u.name AS name,
                u.surname AS surname,
                u.email AS email
            FROM social_network.subscription s
            JOIN social_network.social_user u ON s.subscriber_id = u.id
            WHERE s.target_id = :userId
            """)
    Flux<UserProjection> findSubscribersByTargetId(Integer userId);
}
