create table avp
(
    id              bigserial primary key,
    name            varchar(25),
    season_end_at   date,
    season_start_at date,
    deleted         boolean default false
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY                         NOT NULL,
    first_name VARCHAR(25)                                   NOT NULL,
    last_name  VARCHAR(25)                                   NOT NULL,
    patronymic VARCHAR(25),
    email      VARCHAR(50)                                   NOT NULL,
    phone      VARCHAR(16)                                   NOT NULL,
    avp_id     BIGINT REFERENCES avp (id)                    NOT NULL,
    password   CHARACTER VARYING                             NOT NULL,
    role       VARCHAR(25)                                   NOT NULL,
    locked     BOOLEAN           DEFAULT false               NOT NULL,
    enabled    BOOLEAN           DEFAULT true                NOT NULL,
    deleted    BOOLEAN           DEFAULT false               NOT NULL,
    image      CHARACTER VARYING DEFAULT 'default_image.jpg' NOT NULL
);

create table field_crops
(
    id          bigserial
        primary key,
    coefficient numeric default 1,
    img_link    varchar(255),
    name        varchar(45),
    deleted     boolean default false
);

CREATE TABLE IF NOT EXISTS public.password_tokens
(
    id           BIGSERIAL PRIMARY KEY                          NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE                    NOT NULL,
    expired_at   TIMESTAMP WITHOUT TIME ZONE                    NOT NULL,
    confirmed_at TIMESTAMP WITHOUT TIME ZONE,
    token        CHARACTER VARYING                              NOT NULL,
    user_id      BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE IF NOT EXISTS persistent_logins
(
    username  VARCHAR(64)                 NOT NULL,
    series    VARCHAR(64) PRIMARY KEY     NOT NULL,
    token     VARCHAR(64)                 NOT NULL,
    last_used TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

create table departments
(
    id                      bigserial
        primary key,
    allowed                 boolean default false,
    deleted                 boolean default false,
    max_parallel_irrigation integer default 1,
    name                    varchar(25),
    avp_id                  bigint
        constraint fktpvgry6hae69c2w6rn748ffok
            references avp
);

create table fields
(
    id             bigserial
        primary key,
    archived       boolean default false,
    extra_time     integer default 0,
    name           varchar(100),
    size           numeric,
    department_id  bigint
        constraint fkj63oyblld5h85yqfhxm5dp7an
            references departments,
    field_crops_id bigint
        constraint fktb7peynotb4p1jby5t8cog57q
            references field_crops,
    user_id        bigint
        constraint fkevkcgfm2ljrikj9ffqgd29d6j
            references users on delete cascade,
    status         VARCHAR(25) NOT NULL
);


create table queue
(
    id            bigserial
        primary key,
    created_time  timestamp,
    start_time    timestamp,
    finish_time   timestamp,
    status        varchar(25),
    department_id bigint
        constraint fkmp6ivlcyd6057cp6nh0bpnrw1
            references departments,
    farmer_id     bigint
        constraint fkkymnqqnhqv4mof8nwo6do95y2
            references users on delete cascade,
    field_id      bigint
        constraint fko0qeyebvk5dvcjaa6h4lkbbp1
            references fields,
    operator_id   bigint
        constraint fkcoghnpeght4gip94feq9jrj4q
            references users on delete cascade
);

CREATE TABLE IF NOT EXISTS discussion_topics
(
    id          BIGSERIAL PRIMARY KEY                          NOT NULL,
    title       VARCHAR(255)                                   NOT NULL,
    description CHARACTER VARYING                              NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT now()      NOT NULL,
    status      VARCHAR(25)                                    NOT NULL,
    user_id     BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    views       BIGINT                      DEFAULT 0          NOT NULL,
    messages    BIGINT                      DEFAULT 0          NOT NULL,
    deleted     BOOLEAN                     DEFAULT FALSE      NOT NULL
);

CREATE TABLE IF NOT EXISTS messages
(
    id                  BIGSERIAL PRIMARY KEY                          NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT now()      NOT NULL,
    message             CHARACTER VARYING                              NOT NULL,
    user_id             BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    discussion_topic_id BIGINT REFERENCES discussion_topics ("id")     NOT NULL,
    deleted             BOOLEAN                     DEFAULT FALSE      NOT NULL
);

CREATE TABLE IF NOT EXISTS news
(
    id         BIGSERIAL PRIMARY KEY       NOT NULL,
    title      CHARACTER VARYING           NOT NULL,
    image      CHARACTER VARYING,
    text       CHARACTER VARYING           NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted    BOOLEAN DEFAULT FALSE       NOT NULL
);


CREATE TABLE IF NOT EXISTS news_avp
(
    id         BIGSERIAL PRIMARY KEY       NOT NULL,
    title      CHARACTER VARYING           NOT NULL,
    image      CHARACTER VARYING,
    text       CHARACTER VARYING           NOT NULL,
    avp_id     BIGINT REFERENCES avp (id)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted    BOOLEAN DEFAULT FALSE       NOT NULL
);

CREATE TABLE IF NOT EXISTS fields_history
(
    id          BIGSERIAL PRIMARY KEY         NOT NULL,
    started_at  TIMESTAMP                     NOT NULL,
    ended_at    TIMESTAMP                     NOT NULL,
    field_id    BIGINT REFERENCES fields (id) NOT NULL,
    description CHARACTER VARYING             NOT NULL
);