package com.khoubyari.example.service.test;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.khoubyari.example.service.GithubService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class, FlywayTestExecutionListener.class })
@FlywayTest
public class GithubServiceTest {

	@Autowired
	private GithubService githubService;
	
	@Test
	public void testUpdateGithubData() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPayloadHistory() {
		//githubService.getPayloadHistory();
	}

}
