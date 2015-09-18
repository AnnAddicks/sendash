CREATE table PendingEndpoint(
     clientId BIGINT NOT Null,
     hostName varchar(255) NOT NULL,
     apiKey varchar(255) NOT NULL,
     PRIMARY KEY(clientId, hostName),
     FOREIGN KEY (clientId)
        REFERENCES Client(id)
);

CREATE INDEX pendingEndPointApiAndClientId
    ON PendingEndpoint(clientId, apiKey);