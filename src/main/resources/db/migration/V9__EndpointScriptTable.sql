CREATE TABLE EndpointScript (
	ENDPOINT_ID VARCHAR(36) Not Null,
	SCRIPT_ID INTEGER Not Null,
	PRIMARY KEY (ENDPOINT_ID, SCRIPT_ID),
 	FOREIGN KEY (ENDPOINT_ID)
		REFERENCES Endpoint(ID),
	FOREIGN KEY (SCRIPT_ID)
    		REFERENCES Script(ID)


) ENGINE=InnoDB;

CREATE INDEX endpointScriptEndpointId
    ON EndpointScript(ENDPOINT_ID);
CREATE INDEX endpointScriptScriptId
    ON EndpointScript(SCRIPT_ID);
