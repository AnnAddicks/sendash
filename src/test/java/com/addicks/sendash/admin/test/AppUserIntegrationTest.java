package com.addicks.sendash.admin.test;

import org.flywaydb.test.annotation.FlywayTest;
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

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.dao.jpa.UserRepository;
import com.addicks.sendash.admin.service.EndpointService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

/**
 * Created by ann on 4/23/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class, FlywayTestExecutionListener.class })

@FlywayTest
public class AppUserIntegrationTest {
  private static final Logger log = LoggerFactory.getLogger(AppUserIntegrationTest.class);

  @Autowired
  private UserRepository repository;

  @Autowired
  private EndpointService endpointService;

  @Test
  public void testFlywayAddingUser() {
    log.debug("\n\n\n\n***************************users:" + repository.findAll());
  }

  @Test
  public void testAnother() {
    // log.error(
    // "***********************************************\nendpoints:" +
    // endpointService.findAll());
  }

}
