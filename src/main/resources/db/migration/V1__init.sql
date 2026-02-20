CREATE SCHEMA IF NOT EXISTS social_network;

-- TABLES
CREATE TABLE social_network.social_user
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(20)  NOT NULL,
    surname  VARCHAR(20)  NOT NULL,
    email    VARCHAR(30)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

CREATE TABLE social_network.image
(
    id        SERIAL PRIMARY KEY,
    file_path VARCHAR(512) NOT NULL
);

CREATE TABLE social_network.post
(
    id        SERIAL PRIMARY KEY,
    user_id   INTEGER       NOT NULL,
    post_text VARCHAR(1000) NOT NULL,
    post_date TIMESTAMP     NOT NULL DEFAULT now(),
    image_id  INTEGER       NOT NULL
);

CREATE TABLE social_network.post_comment
(
    id           SERIAL PRIMARY KEY,
    post_id      INTEGER       NOT NULL,
    user_id      INTEGER       NOT NULL,
    comment_text VARCHAR(1000) NOT NULL,
    comment_date TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE TABLE social_network.post_like
(
    id      SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL
);

CREATE TABLE social_network.message
(
    id           SERIAL PRIMARY KEY,
    sender_id    INTEGER       NOT NULL,
    receiver_id  INTEGER       NOT NULL,
    message_text VARCHAR(1000) NOT NULL,
    message_date TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE TABLE social_network.subscription
(
    id            SERIAL PRIMARY KEY,
    subscriber_id INTEGER   NOT NULL,
    target_id     INTEGER   NOT NULL,
    subscribed_at TIMESTAMP NOT NULL  DEFAULT now(),
    CONSTRAINT ck_subscription_not_self CHECK (subscriber_id <> target_id)
);

-- FKs + CASCADE
ALTER TABLE social_network.message
    ADD CONSTRAINT fk_message_sender_id FOREIGN KEY (sender_id) REFERENCES social_network.social_user (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_message_receiver_id FOREIGN KEY (receiver_id) REFERENCES social_network.social_user (id) ON DELETE CASCADE;

ALTER TABLE social_network.post
    ADD CONSTRAINT fk_post_user_id FOREIGN KEY (user_id) REFERENCES social_network.social_user (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_post_image_id FOREIGN KEY (image_id) REFERENCES social_network.image (id) ON DELETE CASCADE;

ALTER TABLE social_network.post_comment
    ADD CONSTRAINT fk_post_comment_post_id FOREIGN KEY (post_id) REFERENCES social_network.post (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_post_comment_user_id FOREIGN KEY (user_id) REFERENCES social_network.social_user (id) ON DELETE CASCADE;

ALTER TABLE social_network.post_like
    ADD CONSTRAINT fk_post_like_post_id FOREIGN KEY (post_id) REFERENCES social_network.post (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_post_like_user_id FOREIGN KEY (user_id) REFERENCES social_network.social_user (id) ON DELETE CASCADE;

ALTER TABLE social_network.subscription
    ADD CONSTRAINT fk_subscription_subscriber_id FOREIGN KEY (subscriber_id) REFERENCES social_network.social_user (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_subscription_target_id FOREIGN KEY (target_id) REFERENCES social_network.social_user (id) ON DELETE CASCADE;

-- UNIQUEs
ALTER TABLE social_network.social_user
    ADD CONSTRAINT uk_social_user_email UNIQUE (email);
ALTER TABLE social_network.image
    ADD CONSTRAINT uk_image_file_path UNIQUE (file_path);
ALTER TABLE social_network.post_like
    ADD CONSTRAINT uk_post_like_unique UNIQUE (post_id, user_id);
ALTER TABLE social_network.subscription
    ADD CONSTRAINT uk_subscription_pair UNIQUE (subscriber_id, target_id);

-- CHECKs
ALTER TABLE social_network.social_user
    ADD CONSTRAINT ck_social_user_role CHECK (role IN ('ROLE_USER', 'ROLE_ADMIN'));

-- INDEXes
CREATE INDEX idx_post_user_id ON social_network.post (user_id);
CREATE INDEX idx_post_date ON social_network.post (post_date);
CREATE INDEX idx_post_comment_post_id ON social_network.post_comment (post_id);
CREATE INDEX idx_post_comment_user_id ON social_network.post_comment (user_id);
CREATE INDEX idx_post_like_post_id ON social_network.post_like (post_id);
CREATE INDEX idx_post_like_user_id ON social_network.post_like (user_id);
CREATE INDEX idx_subscription_subscriber_id ON social_network.subscription (subscriber_id);
CREATE INDEX idx_subscription_target_id ON social_network.subscription (target_id);
CREATE INDEX idx_message_dialog ON social_network.message (sender_id, receiver_id, message_date);
