DROP TABLE IF EXISTS user CASCADE;

CREATE TABLE user
(
    user_id      int,
    id          varchar(20),
    password    varchar(100),
    name        varchar(100),
    age         int,

    PRIMARY KEY (user_id)
);