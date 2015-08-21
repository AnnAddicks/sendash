package com.khoubyari.example.git.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.khoubyari.example.Application;
import com.khoubyari.example.git.GitManager;
import com.khoubyari.example.git.RepositoryProperties;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class GitManagerTest {

	@Autowired
	private GitManager manager;

	@Autowired
	private static RepositoryProperties properties;

	private static final String localRepoLocation = properties.getLocalRepo();
	
	private static final Logger log = LoggerFactory.getLogger(GitManager.class);

	@BeforeClass
	public static void setUp() {
		log.warn("Properties: " + properties);
		log.warn("Repo Location: " + localRepoLocation);
		
		
		File gitDirectory = new File(localRepoLocation);
		gitDirectory.delete();
	}

	@Test
	public void shouldCreateLocalRepo() {
		manager.updateLocalRepository();

		File gitDirectory = new File(localRepoLocation + "/.git");

		assertNotNull(gitDirectory);
		log.error("*********************************");
		log.error("File: " + gitDirectory);
		log.error("*********************************");
		
		assertTrue(gitDirectory.isDirectory());
		assertTrue(gitDirectory.list().length > 1);
	}

	@Test
	public void shouldUpdateExistingLocalRepo() {
		manager.updateLocalRepository();

		File gitDirectory = new File(localRepoLocation + "/.git");

		assertNotNull(gitDirectory);
		assertTrue(gitDirectory.isDirectory());
		assertTrue(gitDirectory.list().length > 1);
	}

}
