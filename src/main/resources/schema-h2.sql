DROP TABLE IF EXISTS user CASCADE;

CREATE TABLE user
(
    uid      bigint    auto_increment,
    email       varchar(100),
    password    varchar(100),
    name        varchar(100),
    nickname    varchar(100),
    address     varchar(100)

    PRIMARY KEY (uid)
);