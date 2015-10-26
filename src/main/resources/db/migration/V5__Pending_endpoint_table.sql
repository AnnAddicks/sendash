CREATE table PENDING_ENDPOINT(
     ID BIGINT NOT NULL AUTO_INCREMENT,
     CLIENT_ID BIGINT NOT Null,
     HOST_NAME varchar(255) NOT NULL,
     API_KEY varchar(255) NOT NULL,
     PRIMARY KEY(ID),
    CONSTRAINT pending_client_host UNIQUE (CLIENT_ID, HOST_NAME),
     FOREIGN KEY (CLIENT_ID)
        REFERENCES Client(ID)
) ENGINE=InnoDB;

CREATE INDEX pendingEndPointApiAndClientId
    ON PENDING_ENDPOINT(CLIENT_ID, API_KEY);