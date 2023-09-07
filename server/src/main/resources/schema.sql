CREATE TABLE IF NOT EXISTS categories (
    id          INTEGER       GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(50)   NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS users (
     id          BIGINT         GENERATED BY DEFAULT AS IDENTITY NOT NULL,
     name        VARCHAR(250)   NOT NULL,
     email       VARCHAR(254)   NOT NULL,
     CONSTRAINT pk_user PRIMARY KEY (id),
     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS locations (
     id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
     lat FLOAT                                   NOT NULL,
     lon FLOAT                                   NOT NULL,
     CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        INTEGER                                 NOT NULL,
    confirmed_requests BIGINT                                  NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    description        VARCHAR(7000)                           NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    location_id        BIGINT                                  NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  BIGINT                                  NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                                 NOT NULL,
    state              INTEGER                                 NOT NULL,
    title              VARCHAR(120)                            NOT NULL,
    views              BIGINT                                  NOT NULL,
    CONSTRAINT pk_events   PRIMARY KEY (id),
    CONSTRAINT fk_events   FOREIGN KEY (category_id)  REFERENCES categories(id),
    CONSTRAINT fk_events_1 FOREIGN KEY (initiator_id) REFERENCES users(id)     ON DELETE CASCADE,
    CONSTRAINT fk_events_2 FOREIGN KEY (location_id)  REFERENCES locations(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests (
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       INTEGER                                 NOT NULL,
    CONSTRAINT pk_requests   PRIMARY KEY (id),
    CONSTRAINT fk_requests   FOREIGN KEY (event_id)     REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_requests_1 FOREIGN KEY (requester_id) REFERENCES users(id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilations (
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR(50)                             NOT NULL,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event_compilation (
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    compilation_id BIGINT                                  NOT NULL,
    event_id       BIGINT                                  NOT NULL,
    CONSTRAINT pk_event_compilation   PRIMARY KEY (id),
    CONSTRAINT fk_event_compilation   FOREIGN KEY (compilation_id) REFERENCES compilations(id) ON DELETE CASCADE,
    CONSTRAINT fk_event_compilation_1 FOREIGN KEY (event_id)       REFERENCES events(id)       ON DELETE CASCADE
);