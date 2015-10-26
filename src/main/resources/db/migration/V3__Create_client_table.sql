CREATE table Client(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255),
    PRIMARY KEY(name)
) ENGINE=InnoDB;

CREATE INDEX clientId
    ON Client(id);