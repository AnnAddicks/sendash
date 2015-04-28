CREATE table PendingEndpoint(
     clientId CHAR(36) NOT Null,
     hostName varchar(255) NOT NULL,
     apiKey varchar(255) NOT NULL,
     PRIMARY KEY(clientId, hostName),
     FOREIGN KEY (clientId)
        REFERENCES Client(id)
);

CREATE INDEX pendingEndPointApiAndClientId
    ON EndPoint(clientId, apiKey);