CREATE TABLE Commit (
	id BIGINT NOT Null,
	payloadId BIGINT Not Null,
	message varchar(255),
	commitTimestamp DATETIME NOT NULL,
	added varchar(255),
	modified varchar(255),
    removed varchar(255),
	PRIMARY KEY (message, commitTimestamp)
);

CREATE INDEX commitId
    ON Commit(id);
CREATE INDEX commitPayloadId
    ON Commit(payloadId);
