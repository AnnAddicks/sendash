CREATE TABLE EndpointScript (
	endpointId CHAR(36) Not Null,
	scriptId BIGINT Not Null,
	PRIMARY KEY (endpointId, scriptId),
 	FOREIGN KEY (endpointId)
		REFERENCES Endpoint(id),
	FOREIGN KEY (scriptId)
    		REFERENCES Script(id)


);

CREATE INDEX endpointScriptEndpointId
    ON EndpointScript(endpointId);
CREATE INDEX endpointScriptScriptId
    ON EndpointScript(scriptId);
