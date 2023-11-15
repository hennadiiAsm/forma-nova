-- create table "user-service".users
-- (
--     id           bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--     version      bigint  NOT NULL,
--     enabled      boolean NOT NULL,
--     email        text    NOT NULL,
--     password     text    NOT NULL,
--     first_name   text    NOT NULL,
--     last_name    text    NOT NULL,
--     birth_date   date    NOT NULL,
--     phone_number text,
--     country      text,
--     city         text
-- );
--
-- CREATE TABLE "user-service".payment_cards
-- (
--     number          text PRIMARY KEY,
--     version         bigint NOT NULL,
--     holder_id       bigint NOT NULL,
--     expiration_date date NOT NULL CHECK (expiration_date > current_date),
--     security_code   text NOT NULL CHECK (length(security_code) IN (3, 4)),
--     FOREIGN KEY (holder_id) REFERENCES "user-service".users (id)
-- );

INSERT INTO users(email, password, firstname, lastname, birthdate, enabled, version)
VALUES ('a@a', 'aaa', 'aaa', 'aaa', '2000-10-10', true, 0);

SELECT * FROM users;