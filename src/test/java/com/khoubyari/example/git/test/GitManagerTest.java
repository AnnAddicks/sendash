package com.khoubyari.example.git.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class})
public class GitManagerTest {
	
	@Autowired
	GitManager manager;
	
	@Autowired
	RepositoryProperties properties;

	@BeforeClass
	public void setUp() {
		File gitDirectory = new File(properties.getLocalRepo());
		gitDirectory.delete();
	}
	
	@Test
	public void shouldCreateLocalRepo() {
		manager.updateLocalRepository();
		
		File gitDirectory = new File(properties.getLocalRepo() + "/.git");
		
		assertNotNull(gitDirectory);
		assertTrue(gitDirectory.isDirectory());
		assertTrue(gitDirectory.list().length > 1);
	}
	
	@Test
	public void shouldUpdateExistingLocalRepo() {
		manager.updateLocalRepository();
	}

}
