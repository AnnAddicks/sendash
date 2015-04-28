CREATE table Client(
    id CHAR(36) UNIQUE NOT NULL,
    name varchar(255),
    PRIMARY KEY(name)
);

CREATE INDEX clientId
    ON Client(id);