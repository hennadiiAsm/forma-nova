
CREATE TABLE reviews (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    version bigint NOT NULL,
    created_at timestamp GENERATED ALWAYS AS now() NOT NULL,
    author_id bigint NOT NULL,
    author_first_name text NOT NULL,
    author_last_name text NOT NULL,
    target_id bigint NOT NULL,
    skill_name text,
    grade bytea NOT NULL,
    title text,
    content text
);
