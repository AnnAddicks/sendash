CREATE TABLE PERSON (
	id CHAR(36) NOT Null,
	email varchar(255) not null UNIQUE,
	first_name varchar(255) not null,
	last_name varchar(255) not null
);

