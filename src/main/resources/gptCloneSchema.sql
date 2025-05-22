CREATE TABLE user (
                       id                  BIGSERIAL PRIMARY KEY,
                       name                VARCHAR(100)   NOT NULL,
                       email               VARCHAR(255)   NOT NULL UNIQUE,
                       password            VARCHAR(255)   NOT NULL,
                       extra_preferences   JSONB
);

-- 2) Threads table
CREATE TABLE threads (
                         id            BIGSERIAL      PRIMARY KEY,
                         name          VARCHAR(255)   NOT NULL,
                         created_at    TIMESTAMPTZ    NOT NULL DEFAULT now(),
                         user_id       BIGINT         NOT NULL
                             REFERENCES user(id)
                                 ON DELETE CASCADE
);

-- 3) Messages table
CREATE TABLE message (
                          id                 BIGSERIAL    PRIMARY KEY,
                          content            TEXT         NOT NULL,
                          thread_id          BIGINT       NOT NULL
                              REFERENCES threads(id)
                                  ON DELETE CASCADE,
                          is_llm_generated   BOOLEAN      NOT NULL DEFAULT FALSE
);

-- 4) Models table
CREATE TABLE models (
                        name  VARCHAR(100) PRIMARY KEY,
                        url   TEXT         NOT NULL
);