package com.khoubyari.example.domain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:application.yml", prefix = "git", ignoreUnknownFields = false)
public class RepositoryProperties {
	
	private String localRepo;
	private String remoteRepo;
	private String username;
	private String password;
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localRepo == null) ? 0 : localRepo.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((remoteRepo == null) ? 0 : remoteRepo.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepositoryProperties other = (RepositoryProperties) obj;
		if (localRepo == null) {
			if (other.localRepo != null)
				return false;
		} else if (!localRepo.equals(other.localRepo))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (remoteRepo == null) {
			if (other.remoteRepo != null)
				return false;
		} else if (!remoteRepo.equals(other.remoteRepo))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RepositoryProperties [localRepo=" + localRepo + ", remoteRepo=" + remoteRepo + ", username=" + username
				+ ", password=" + password + "]";
	}

	
	

}
