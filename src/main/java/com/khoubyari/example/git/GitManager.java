package com.khoubyari.example.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class GitManager {

	@Autowired
	private RepositoryProperties repositoryProperties;

	public GitManager() {

	}

	public Git openRepository() throws IOException, IllegalStateException, GitAPIException {
		File gitDirectory = new File(repositoryProperties.getLocalRepo());
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.addCeilingDirectory(gitDirectory);
		builder.findGitDir(gitDirectory);
		
		Git git;
		if (builder.getGitDir() == null) {
			git = Git.init().setDirectory(gitDirectory.getParentFile()).call();
		} else {
			git = new Git(builder.build());
		}
		return git;
	}
}
