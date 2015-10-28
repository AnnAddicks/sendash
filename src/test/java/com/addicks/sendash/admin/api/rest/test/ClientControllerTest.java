package com.addicks.sendash.admin.api.rest.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
import com.addicks.sendash.admin.api.rest.ClientController;
import com.addicks.sendash.admin.api.rest.UserController;
import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.IClientService;
import com.addicks.sendash.admin.test.JsonUtility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

@Profile("test")
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class, FlywayTestExecutionListener.class })
@FlywayTest
public class ClientControllerTest extends ControllerTest {

  @InjectMocks
  private UserController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private IClientService clientService;

  private MockMvc mvc;

  private byte[] clientJson;

  @Before
  public void initTests() throws JsonParseException, JsonMappingException, IOException, Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    Client client = JsonUtility.loadObjectFromJson(JsonUtility.CLIENT_JSON, Client.class);
    clientJson = toJson(client);
  }

  @Test
  public void testCreateClient() throws Exception {
    MvcResult result = mvc
        .perform(post(ClientController.REQUEST_MAPPING).content(clientJson)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrlPattern(SERVER + ClientController.REQUEST_MAPPING + "/[0-9]+"))
        .andReturn();
    long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

    assertNotNull(id);
    assertThat("id", id, greaterThan(0L));
  }

  @Test
  public void testGetAllClients() throws Exception {
    User user = new User();
    user.setId(1L);

    MvcResult result = mvc
        .perform(get(ClientController.REQUEST_MAPPING).contentType(MediaType.APPLICATION_JSON)
            .principal(getPrincipal()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    String clientString = result.getResponse().getContentAsString();

    List<Client> returnedClients = JsonUtility.loadObjectListFromString(clientString, Client.class);
    Page<Client> savedClients = clientService.findAll(user, 0, 50);

    assertEquals(returnedClients, savedClients.getContent());
  }

  @Test
  public void shouldReturnClient() throws Exception {
    MvcResult result = mvc
        .perform(get(ClientController.REQUEST_MAPPING + "/1")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    String clientString = result.getResponse().getContentAsString();

    Client returnedClient = JsonUtility.loadObjectFromString(clientString, Client.class);
    Client savedClient = clientService.findById(1L);
    assertEquals(returnedClient, savedClient);

  }

  @Test
  public void testUpdateClient() {
    fail("Not yet implemented");
  }

  @Test
  public void testDeleteClient() {
    fail("Not yet implemented");
  }

}
