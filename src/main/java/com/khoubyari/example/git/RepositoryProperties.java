package com.khoubyari.example.git;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "git", ignoreUnknownFields = false)
@Component
public class RepositoryProperties {
	@NotNull
	private String localRepo = "localRepo";
	
	@NotNull
	private String remoteRepo ="remoteRepo";

	public String getLocalRepo() {
		return localRepo;
	}

	public void setLocalRepo(String localRepo) {
		this.localRepo = localRepo;
	}

	public String getRemoteRepo() {
		return remoteRepo;
	}

	public void setRemoteRepo(String remoteRepo) {
		this.remoteRepo = remoteRepo;
	}

	@Override
	public String toString() {
		return "RepositoryProperties [localRepo=" + localRepo + ", remoteRepo=" + remoteRepo + "]";
	}
	

}
