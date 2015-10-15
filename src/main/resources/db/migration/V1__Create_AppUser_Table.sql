CREATE TABLE PERSON (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	EMAIL varchar(255) UNIQUE,
	PASSWORD varchar(255) NOT NULL,
	FIRST_NAME varchar(255) NOT NULL,
	LAST_NAME varchar(255) NOT NULL,
	PRIMARY KEY(ID)
);

CREATE INDEX personId
    ON Person(EMAIL);