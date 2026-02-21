CREATE SCHEMA IF NOT EXISTS social_network;

-- H2 автоматически приведет social_user -> SOCIAL_USER
CREATE TABLE IF NOT EXISTS social_network.social_user (
                                           id SERIAL PRIMARY KEY,
                                           name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
    );

-- 1. Администратор
INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Admin', 'Super', 'admin@social.net', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_ADMIN');

-- 2. Обычные пользователи
INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Ivan', 'Ivanov', 'ivan@mail.com', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Petr', 'Petrov', 'petr@mail.com', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Anna', 'Sidorova', 'anna@mail.com', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Maria', 'Kuznetsova', 'maria@test.com', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Dmitry', 'Smirnov', 'dmitry@work.org', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Olga', 'Popova', 'olga@gym.io', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Sergey', 'Volkov', 'sergey@dev.net', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('Elena', 'Morozova', 'elena@art.com', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');

INSERT INTO social_network.social_user (name, surname, email, password, role)
VALUES ('John', 'Doe', 'johndoetest@gmail.com', '$2a$10$VmmK3FviOlmNLmUPk4zu2OqDqVVF/xZu7NBIvatk7IORrU8fb1bm.', 'ROLE_USER');
