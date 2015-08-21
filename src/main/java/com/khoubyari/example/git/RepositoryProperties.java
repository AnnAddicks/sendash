package com.khoubyari.example.git;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:application.yml", prefix = "git", ignoreUnknownFields = false)
public class RepositoryProperties {
	@NotNull
	private String localRepo;
	
	@NotNull
	private String remoteRepo;

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
