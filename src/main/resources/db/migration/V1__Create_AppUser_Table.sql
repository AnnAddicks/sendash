CREATE TABLE PERSON (
	id CHAR(36) NOT Null,
	email varchar(255),
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	PRIMARY KEY (email)
);

CREATE INDEX personId
    ON Person(id);