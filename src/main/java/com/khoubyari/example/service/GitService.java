package com.khoubyari.example.service;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.khoubyari.example.domain.properties.RepositoryProperties;

@Service
@EnableConfigurationProperties
public class GitService {

	@Autowired
	private RepositoryProperties repositoryProperties;
	
	private static final Logger log = LoggerFactory.getLogger(GitService.class);

	public GitService() {

	}

	public void updateLocalRepository() {
		Git git = null;
		try {
		    git = openRepository();
			
			
			
		}
		catch(IOException | IllegalStateException | GitAPIException ex) {
			log.error("An exception occured while updating the local repo:", ex);
		}
		finally {
			if(git != null && git.getRepository() != null) {
				// workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=474093
				git.getRepository().close();
			}
		}
	}
	
	private Git openRepository() throws IOException, IllegalStateException, GitAPIException {
		log.error("*********************************");
        log.error("Opening Repository " + repositoryProperties.getRemoteRepo());
        log.error("*********************************");
		File gitDirectory = new File(repositoryProperties.getLocalRepo());
		
		
		Git git;
		if (gitDirectory.exists() && gitDirectory.list().length > 1) {
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			builder.addCeilingDirectory(gitDirectory);
			builder.findGitDir(gitDirectory);
			git = new Git(builder.build());
			FetchResult result = git.fetch()
					.setCheckFetchedObjects(true)
					.setCredentialsProvider(getRemoteCredentials())
					.call();
			log.error("*********************************");
			log.error("The result of fetching the repo:" + result.getMessages());
			log.error("*********************************");
		} else {
			git = cloneRepository(gitDirectory);
		}
		return git;
	}
	
	private Git cloneRepository(File gitDirectory) throws InvalidRemoteException, TransportException, GitAPIException {
		if(!gitDirectory.exists()){
			gitDirectory.mkdirs();
		}
		gitDirectory.delete();
       
	    log.error("*********************************");
        log.error("Cloning from " + repositoryProperties.getRemoteRepo() + " to " + gitDirectory);
        log.error("*********************************");
        try (Git result = Git.cloneRepository()
                .setURI(repositoryProperties.getRemoteRepo())
                .setDirectory(gitDirectory)
                .setCredentialsProvider(getRemoteCredentials())
                .call()) {
	        
	        log.debug("Having repository: " + result.getRepository().getDirectory());
	        return result;
        }
	}
	
	private CredentialsProvider getRemoteCredentials() {
		CredentialsProvider provider = new UsernamePasswordCredentialsProvider(repositoryProperties.getUsername(), repositoryProperties.getPassword());
		return provider;
	}
}
