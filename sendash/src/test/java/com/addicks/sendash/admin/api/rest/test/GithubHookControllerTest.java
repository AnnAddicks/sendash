package com.addicks.sendash.admin.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.addicks.sendash.admin.api.rest.GithubHookController;
import com.addicks.sendash.admin.domain.Payload;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

/**
 * Created by ann on 5/27/15.
 */
@Profile("test")
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    SqlScriptsTestExecutionListener.class, DbUnitTestExecutionListener.class,
    FlywayTestExecutionListener.class })
@FlywayTest
public class GithubHookControllerTest extends ControllerTest {

  @InjectMocks
  private GithubHookController controller;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void initTests() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void shouldCreate() throws Exception {

    byte[] json = readObjectFromJsonFile("json/githubPayload.json", Payload.class);

    mvc.perform(post(GithubHookController.REQUEST_MAPPING).content(json)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

  }

}
