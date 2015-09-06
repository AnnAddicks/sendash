package com.addicks.sendash.admin.api.rest.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.addicks.sendash.admin.api.rest.GithubHookController;
import com.addicks.sendash.admin.domain.Payload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

/**
 * Created by ann on 5/27/15.
 */
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

    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("json/githubPayload.json").getFile());
    String path = file.getPath();

    ObjectMapper mapper = new ObjectMapper();
    Payload payload = mapper.readValue(new File(path), Payload.class);
    byte[] json = toJson(payload);

    /*
     * MvcResult result = mvc.perform(post(GithubHookController.REQUEST_MAPPING)
     * .content(json) .contentType(MediaType.APPLICATION_JSON)
     * .accept(MediaType.APPLICATION_JSON)) .andExpect(status().isOk())
     * .andReturn();
     * 
     */

  }

  // match redirect header URL (aka Location header)
  private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
    return new ResultMatcher() {
      @Override
      public void match(MvcResult result) {
        Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
        assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
      }
    };
  }

  private byte[] toJson(Object r) throws Exception {
    ObjectMapper map = new ObjectMapper();
    return map.writeValueAsString(r).getBytes();
  }

  private String loadFile(String pathToFile) throws IOException {
    return new String(Files.readAllBytes(Paths.get(pathToFile)));

  }

}
