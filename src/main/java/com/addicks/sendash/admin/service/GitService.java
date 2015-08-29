package com.addicks.sendash.admin.service;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.domain.properties.RepositoryProperties;

/**
 * A simple service that will update the repository on the server.
 * 
 * @author annaddicks
 *
 */
@Service
@EnableConfigurationProperties
public class GitService {

	@Autowired
	private RepositoryProperties repositoryProperties;

	private static final Logger log = LoggerFactory.getLogger(GitService.class);

	public GitService() {

	}

	/**
	 * Clones the remote repository if it does not exist or update a current
	 * one.
	 */
	public void updateLocalRepository() {
		if (repositoryProperties.getUsername() == null || repositoryProperties.getUsername().isEmpty()
				|| repositoryProperties.getPassword() == null || repositoryProperties.getPassword().isEmpty()
				|| repositoryProperties.getUsername().contains("git.username")
				|| repositoryProperties.getPassword().contains("git.pass")) {
			log.error(
					"The username and password must be set before starting this application.  Use the parameters -Dgit.username=yourUsername and -Dgit.password=yourPassword.");
		} else {
			Git git = null;
			try {
				git = openRepository();
			} catch (IOException | IllegalStateException | GitAPIException ex) {
				log.error("An exception occured while updating the local repo:", ex);
			} finally {
				if (git != null && git.getRepository() != null) {
					// workaround for
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=474093
					git.getRepository().close();
				}
			}
		}
	}

	private Git openRepository() throws IOException, IllegalStateException, GitAPIException {
		File gitDirectory = new File(repositoryProperties.getLocalRepo());
		Git git;

		if (gitDirectory.exists() && gitDirectory.list().length > 1) {
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			builder.addCeilingDirectory(gitDirectory);
			builder.findGitDir(gitDirectory);
			git = new Git(builder.build());
			git.fetch().setCheckFetchedObjects(true).setCredentialsProvider(getRemoteCredentials()).call();
		} else {
			git = cloneRepository(gitDirectory);
		}
		return git;
	}

	private Git cloneRepository(File gitDirectory) throws InvalidRemoteException, TransportException, GitAPIException {
		if (!gitDirectory.exists()) {
			gitDirectory.mkdirs();
		}
		gitDirectory.delete();

		log.error("*********************************");
		log.error("Cloning from " + repositoryProperties.getRemoteRepo() + " to " + gitDirectory);
		log.error("*********************************");
		try (Git result = Git.cloneRepository().setURI(repositoryProperties.getRemoteRepo()).setDirectory(gitDirectory)
				.setCredentialsProvider(getRemoteCredentials()).call()) {

			log.debug("Having repository: " + result.getRepository().getDirectory());
			return result;
		}
	}

	private CredentialsProvider getRemoteCredentials() {
		CredentialsProvider provider = new UsernamePasswordCredentialsProvider(repositoryProperties.getUsername(),
				repositoryProperties.getPassword());
		return provider;
	}
}
