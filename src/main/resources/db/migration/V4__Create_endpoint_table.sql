CREATE table EndPoint(
    ID BIGINT NOT Null AUTO_INCREMENT,
    CLIENT_ID BIGINT NOT Null,
    HOST_NAME varchar(255),
    API_KEY varchar(255) UNIQUE,
    UPDATE_SCRIPT_REQUEST DATETIME,
    PRIMARY KEY(ID),
    FOREIGN KEY (CLIENT_ID)
        REFERENCES Client(ID)
) ENGINE=InnoDB;

CREATE INDEX endPointId
    ON EndPoint(CLIENT_ID, HOST_NAME);

CREATE INDEX endPointApiAndClientId
    ON EndPoint(CLIENT_ID, API_KEY);

INSERT INTO Client (id, name) VALUES (1, 'A Very Important Client');
INSERT INTO EndPoint (ID, CLIENT_ID, HOST_NAME, API_KEY) VALUES (3, 1, 'Super Important host name.', 'H9c!!!Asdf5WgaP');