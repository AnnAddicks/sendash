package com.addicks.sendash.admin.service.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.domain.Payload;
import com.addicks.sendash.admin.service.GithubService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class, FlywayTestExecutionListener.class })
@FlywayTest
public class GithubServiceTest {

  @Autowired
  private GithubService githubService;

  private static final String PAYLOAD_FILE = "/json/githubPayload.json";

  private static Payload payload;

  @BeforeClass
  public static void setUp() throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = Test.class.getResourceAsStream(PAYLOAD_FILE);

    payload = mapper.readValue(is, Payload.class);
    payload.setReceivedTimestamp(Calendar.getInstance().getTime());
  }

  @Test
  public void testUpdateGithubData() {
    githubService.updateGithubData(payload);
  }

  @FlywayTest
  @Test
  public void testGetPayloadHistory() {
    githubService.updateGithubData(payload);

    Iterable<Payload> payloads = githubService.getPayloadHistory();
    Iterable<Payload> expected = Arrays.asList(payload);

    assertThat(payloads, hasItem(notNullValue(Payload.class)));
    assertThat(expected, is(payloads));

  }

}
