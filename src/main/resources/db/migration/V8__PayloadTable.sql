CREATE TABLE Payload (
	id BIGINT NOT Null,
	receivedTimestamp DATETIME NOT NULL,
	ref varchar(255),
	before varchar(255),
    after varchar(255),
	PRIMARY KEY (ref)
);

CREATE INDEX payloadId
    ON Payload(id);

CREATE INDEX payloadTime
    ON Payload(receivedTimestamp);

