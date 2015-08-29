package com.addicks.sendash.admin.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.domain.properties.RepositoryProperties;
import com.addicks.sendash.admin.service.GitService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
public class GitServiceTest {

	@Autowired
	private GitService gitService;

	@Autowired
	private RepositoryProperties properties;

	
	private static final Logger log = LoggerFactory.getLogger(GitService.class);
	
	@Test
	public void shouldCreateLocalRepo() throws IOException {
		File gitDirectory = new File(properties.getLocalRepo());
		deleteDirectory(gitDirectory);
		//FileUtils.delete(gitDirectory);
		gitService.updateLocalRepository();

		assertNotNull(gitDirectory);
		assertTrue(gitDirectory.isDirectory());
		assertTrue(gitDirectory.list().length > 1);
	}

	@Test
	public void shouldUpdateExistingLocalRepo() {
		gitService.updateLocalRepository();

		File gitDirectory = new File(properties.getLocalRepo());
		
		assertNotNull(gitDirectory);
		assertTrue(gitDirectory.isDirectory());
		assertTrue(gitDirectory.list().length > 1);
	}
	
	private void deleteDirectory(File directorToDelete) {
		File[] files = directorToDelete.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					deleteDirectory(f);
				} else {
					f.delete();
				}
			}
		}
		directorToDelete.delete();
	}
	

}
