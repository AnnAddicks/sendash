package com.khoubyari.example.service;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.FetchResult;
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
			FetchResult result = git.fetch().setCheckFetchedObjects(true).call();
			
			log.error("*********************************");
			log.error("The result of fetching the repo:" + result);
			log.error("*********************************");
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
		File gitDirectory = new File(repositoryProperties.getLocalRepo());
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.addCeilingDirectory(gitDirectory);
		builder.findGitDir(gitDirectory);
		
		Git git;
		if (builder.getGitDir() == null) {
			git = cloneRepository(gitDirectory);
		} else {
			git = new Git(builder.build());
		}
		return git;
	}
	
	private Git cloneRepository(File gitDirectory) throws InvalidRemoteException, TransportException, GitAPIException {
		gitDirectory.delete();
       
	    log.error("*********************************");
        log.error("Cloning from " + repositoryProperties.getRemoteRepo() + " to " + gitDirectory);
        log.error("*********************************");
        try (Git result = Git.cloneRepository()
                .setURI(repositoryProperties.getRemoteRepo())
                .setDirectory(gitDirectory)
                .call()) {
	        
	        log.debug("Having repository: " + result.getRepository().getDirectory());
	        return result;
        }

	}
}
