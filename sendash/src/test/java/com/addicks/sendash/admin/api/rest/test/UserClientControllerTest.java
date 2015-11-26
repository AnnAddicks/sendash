package com.addicks.sendash.admin.api.rest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

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
import com.addicks.sendash.admin.api.rest.UserClientController;
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
public class UserClientControllerTest extends ControllerTest {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(UserClientControllerTest.class);

  @InjectMocks
  private UserClientController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private IUserService userService;

  private MockMvc mvc;

  @Before
  public void initTests() throws JsonParseException, JsonMappingException, IOException, Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void shouldReturnOneUser() throws Exception {
    MvcResult result = mvc
        .perform(get(UserClientController.REQUEST_MAPPING + "/get-user/for-client/1")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .principal(getPrincipal()))
        .andExpect(status().isOk()).andReturn();
    String userString = result.getResponse().getContentAsString();

    List<User> returnedUsers = JsonUtility.loadObjectListFromString(userString, User.class);

    assertNotNull(returnedUsers);
    assertTrue(returnedUsers.size() == 1);
  }

}
