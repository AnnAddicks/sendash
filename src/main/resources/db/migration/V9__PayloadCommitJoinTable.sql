CREATE TABLE PayloadCommitJoin (
	payloadId BIGINT NOT Null,
	commitId BIGINT NOT Null,
	PRIMARY KEY (payloadId, commitId),
	FOREIGN KEY (payloadId)
            REFERENCES Payload(id),
    FOREIGN KEY (commitId)
            REFERENCES Commit(id)
);

CREATE INDEX payloadIdJoinTable
    ON PayloadCommitJoin(payloadId);

CREATE INDEX commitIdJoinTable
    ON PayloadCommitJoin(commitId);

