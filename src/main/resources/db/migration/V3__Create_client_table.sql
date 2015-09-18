CREATE table Client(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255),
    PRIMARY KEY(name)
);

CREATE INDEX clientId
    ON Client(id);