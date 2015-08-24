package com.khoubyari.example.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.flywaydb.test.junit.FlywayTestExecutionListener;
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
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.khoubyari.example.Application;
import com.khoubyari.example.domain.properties.RepositoryProperties;
import com.khoubyari.example.service.GitService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class, FlywayTestExecutionListener.class })
public class GitServiceTest {

	@Autowired
	private GitService gitService;

	@Autowired
	private RepositoryProperties properties;

	
	private static final Logger log = LoggerFactory.getLogger(GitService.class);
	@Test
	public void shouldCreateLocalRepo() {
		File gitDirectory = new File(properties.getLocalRepo());
		gitDirectory.delete();
		gitService.updateLocalRepository();

		assertNotNull(gitDirectory);
		log.error("*********************************");
		log.error("File: " + gitDirectory);
		log.error("*********************************");
		
		assertTrue(gitDirectory.isDirectory());
		assertTrue(gitDirectory.list().length > 1);
	}

	@Test
	public void shouldUpdateExistingLocalRepo() {
		gitService.updateLocalRepository();

		File gitDirectory = new File(properties.getLocalRepo() + "/.git");

		assertNotNull(gitDirectory);
		assertTrue(gitDirectory.isDirectory());
		assertTrue(gitDirectory.list().length > 1);
	}

}