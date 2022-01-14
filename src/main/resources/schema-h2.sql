DROP TABLE IF EXISTS user CASCADE;

CREATE TABLE user
(
    user_id      LONG    auto_increment,
    id          varchar(20),
    password    varchar(100),
    name        varchar(100),
    age         INTEGER,

    PRIMARY KEY (user_id)
);