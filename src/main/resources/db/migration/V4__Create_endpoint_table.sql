CREATE table EndPoint(
    ID BIGINT NOT Null,
    CLIENT_ID BIGINT NOT Null,
    HOST_NAME varchar(255),
    API_KEY varchar(255) UNIQUE,
    UPDATE_SCRIPT_REQUEST DATETIME,
    PRIMARY KEY(CLIENT_ID, HOST_NAME),
    FOREIGN KEY (CLIENT_ID)
        REFERENCES Client(ID)
);

CREATE INDEX endPointId
    ON EndPoint(ID);

CREATE INDEX endPointApiAndClientId
    ON EndPoint(CLIENT_ID, API_KEY);

INSERT INTO Client (id, name) VALUES (1, 'Client 1');
INSERT INTO EndPoint (ID, CLIENT_ID, HOST_NAME, API_KEY) VALUES (2, 1, 'another host name.', 'H9c!!!A5WgaP');