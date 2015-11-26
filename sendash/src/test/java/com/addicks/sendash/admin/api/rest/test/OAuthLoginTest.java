package com.addicks.sendash.admin.api.rest.test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.addicks.sendash.admin.Application;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class, FlywayTestExecutionListener.class })
@FlywayTest
public class OAuthLoginTest extends ControllerTest {
  private static final Logger log = LoggerFactory.getLogger(OAuthLoginTest.class);

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  private String username = "test@test.com";

  private String password = "password";

  @Before
  public void initTests() throws JsonParseException, JsonMappingException, IOException, Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void shouldLogin() throws Exception {
    mvc.perform(post("/oauth/token")
        .header("Authorization", "Basic c2VuZGFzaFdlYkFwcDpHb0dvVkNIZWNrUHJvQXBw")
        .param("grant_type", "password").param("username", username).param("password", password)
        .param("scope", "read write").param("client_id", "sendashWebApp")
        .param("client_secret", "GoGoVCHeckProApp").contentType("application/x-www-form-urlencoded")
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.access_token", notNullValue()))
        .andExpect(jsonPath("$.token_type", is("bearer")))
        .andExpect(jsonPath("$.refresh_token", notNullValue()))
        .andExpect(jsonPath("$.expires_in", notNullValue()))
        .andExpect(jsonPath("$.scope", is("read write")));

  }

  @Test
  public void shouldNotLogin() throws Exception {
    mvc.perform(post("/oauth/token").with(httpBasic("t@test.com", "password"))
        .param("grant_type", "password").param("username", "t@test.com").param("password", password)
        .param("scope", "read write").param("client_id", "sendashWebApp")
        .param("client_secret", "GoGoVCHeckProApp")
        .contentType("application/x-www-form-urlencoded")).andExpect(status().isUnauthorized());
  }

}
