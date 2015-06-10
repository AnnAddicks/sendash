CREATE table Script(
     id BIGINT Not Null AUTO_INCREMENT,
     scriptName varchar(200) PRIMARY KEY,
     scriptLastUpdated DATETIME,
     data varchar(200)
);
