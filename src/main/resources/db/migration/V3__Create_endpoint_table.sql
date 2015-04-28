CREATE table EndPoint(
    id CHAR(36) NOT Null,
    clientId CHAR(36) NOT Null,
    hostName varchar(255),
    apiKey varchar(255) UNIQUE,
    forceUpdate TINYINT(1) DEFAULT 0,
    PRIMARY KEY(clientId, hostName),
);

CREATE INDEX endPointId
    ON EndPoint(id);

CREATE INDEX endPointApiAndClientId
    ON EndPoint(clientId, apiKey);