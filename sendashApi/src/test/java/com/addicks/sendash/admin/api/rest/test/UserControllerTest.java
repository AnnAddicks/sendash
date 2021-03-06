package com.addicks.sendash.admin.api.rest.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.api.rest.UserController;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.IUserService;
import com.addicks.sendash.admin.test.JsonUtility;
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
public class UserControllerTest extends ControllerTest {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

  @InjectMocks
  private UserController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private IUserService userService;

  private MockMvc mvc;

  private byte[] userJson;

  @Before
  public void initTests() throws JsonParseException, JsonMappingException, IOException, Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    // Skip serialization step to maintain password
    userJson = JsonUtility.loadByteArrayFromJsonFile(JsonUtility.USER_JSON);
  }

  @Test
  public void shouldCreateUser() throws Exception {
    byte[] userUiJson = JsonUtility.loadByteArrayFromJsonFile(JsonUtility.USER_UI_JSON);

    MvcResult result = mvc
        .perform(post(UserController.REQUEST_MAPPING).content(userUiJson)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .principal(getPrincipal()))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrlPattern(SERVER + UserController.REQUEST_MAPPING + "/[0-9]+"))
        .andReturn();
    long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

    assertNotNull(id);
    assertThat("id", id, greaterThan(0L));
  }

  @Test
  public void shouldReturnUser() throws Exception {
    MvcResult result = mvc.perform(get(UserController.REQUEST_MAPPING + "/1")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    String userString = result.getResponse().getContentAsString();

    User returnedUser = JsonUtility.loadObjectFromString(userString, User.class);
    User savedUser = userService.findById(1L);

    assertEquals(returnedUser, savedUser);
  }
}
