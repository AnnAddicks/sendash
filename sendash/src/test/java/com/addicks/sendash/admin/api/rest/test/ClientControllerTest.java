package com.addicks.sendash.admin.api.rest.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.data.domain.Page;
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
import com.addicks.sendash.admin.api.rest.ClientController;
import com.addicks.sendash.admin.api.rest.UserController;
import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.domain.json.ClientUI;
import com.addicks.sendash.admin.service.IClientService;
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
public class ClientControllerTest extends ControllerTest {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(ClientControllerTest.class);

  private static final String RESOURCE_LOCATION_PATTERN = "http://localhost"
      + ClientController.REQUEST_MAPPING + "/[0-9]+";

  @InjectMocks
  private UserController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private IClientService clientService;

  private MockMvc mvc;

  private byte[] clientUIJson;

  @Before
  public void initTests() throws JsonParseException, JsonMappingException, IOException, Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    ClientUI clientUI = JsonUtility.loadObjectFromJson(JsonUtility.CLIENT_UI_JSON, ClientUI.class);
    clientUIJson = toJson(clientUI);
  }

  @Test
  public void shouldCreateClient() throws Exception {

    MvcResult result = mvc
        .perform(post(ClientController.REQUEST_MAPPING).content(clientUIJson)
            .contentType(MediaType.APPLICATION_JSON).principal(getPrincipal())
            .accept(MediaType.APPLICATION_JSON))
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
  @FlywayTest
  public void shouldCreateRetrieveAndUpdateClient() throws Exception {
    Client newClient = JsonUtility.loadObjectFromJson(JsonUtility.CLIENT_UI_JSON, ClientUI.class)
        .getClientData();
    byte[] newClientJson = toJson(newClient);

    // Create
    MvcResult result = mvc
        .perform(post(ClientController.REQUEST_MAPPING).content(clientUIJson)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .principal(getPrincipal()))
        .andExpect(status().isCreated()).andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
        .andReturn();
    long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

    // Retrieve
    mvc.perform(get(ClientController.REQUEST_MAPPING + "/" + id).accept(MediaType.APPLICATION_JSON)
        .principal(getPrincipal())).andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) id)))
        .andExpect(jsonPath("$.name", is(newClient.getName())));

    newClient.setId(id);
    newClient.setName("Anna Banana");
    newClientJson = toJson(newClient);

    // Update
    result = mvc.perform(put(ClientController.REQUEST_MAPPING + "/" + id).content(newClientJson)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .principal(getPrincipal())).andExpect(status().isNoContent()).andReturn();

    // Verify Update
    mvc.perform(get(ClientController.REQUEST_MAPPING + "/" + id).accept(MediaType.APPLICATION_JSON)
        .principal(getPrincipal())).andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) id)))
        .andExpect(jsonPath("$.name", is(newClient.getName())));
  }

  @Test
  @FlywayTest
  public void shouldCreateRetrieveDeleteClient() throws Exception {
    ClientUI newClientUI = JsonUtility.loadObjectFromJson(JsonUtility.CLIENT_UI_JSON,
        ClientUI.class);
    Client newClient = newClientUI.getClientData();

    // Create
    MvcResult result = mvc
        .perform(post(ClientController.REQUEST_MAPPING).content(clientUIJson)
            .contentType(MediaType.APPLICATION_JSON).principal(getPrincipal())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrlPattern(SERVER + ClientController.REQUEST_MAPPING + "/[0-9]+"))
        .andReturn();
    long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

    // Retrieve
    mvc.perform(get(ClientController.REQUEST_MAPPING + "/" + id).accept(MediaType.APPLICATION_JSON)
        .principal(getPrincipal())).andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) id)))
        .andExpect(jsonPath("$.name", is(newClient.getName())));

    // Delete
    mvc.perform(delete(ClientController.REQUEST_MAPPING + "/" + id).principal(getPrincipal()))
        .andExpect(status().isNoContent());

    // Expect that we cannot retrieve the deleted id
    mvc.perform(get(ClientController.REQUEST_MAPPING + "/" + id).accept(MediaType.APPLICATION_JSON)
        .principal(getPrincipal())).andExpect(status().isNotFound());
  }

}
