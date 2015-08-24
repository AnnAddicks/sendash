package com.khoubyari.example.domain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:application.yml", prefix = "git", ignoreUnknownFields = false)
public class RepositoryProperties {
	
	private String localRepo;
	
	private String remoteRepo;
	
	public RepositoryProperties() {
		
	}

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
