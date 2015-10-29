package com.addicks.sendash.admin.api.rest.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
import com.addicks.sendash.admin.api.rest.EndpointController;
import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.IEndpointService;
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
public class EndpointControllerTest extends ControllerTest {
  @InjectMocks
  private ClientController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private IEndpointService endpointService;

  private MockMvc mvc;

  private byte[] endpointJson;

  @Before
  public void initTests() throws JsonParseException, JsonMappingException, IOException, Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    Endpoint endpoint = JsonUtility.loadObjectFromJson(JsonUtility.ENDPOINT_JSON, Endpoint.class);
    endpointJson = toJson(endpoint);
  }

  // @Test
  public void testCreateEndpoint() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetAllEndpoints() throws Exception {
    User user = new User();
    user.setId(1L);

    MvcResult result = mvc
        .perform(get(EndpointController.REQUEST_MAPPING).contentType(MediaType.APPLICATION_JSON)
            .principal(getPrincipal()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    String endpointString = result.getResponse().getContentAsString();
    List<Endpoint> returnedEndpoints = JsonUtility.loadObjectListFromString(endpointString,
        Endpoint.class);
    Page<Endpoint> savedEndpoints = endpointService.findAll(user, 0, 50);

    assertEquals(returnedEndpoints, savedEndpoints.getContent());
  }

  @Test
  public void testGetEndpoint() throws Exception {
    MvcResult result = mvc
        .perform(get(EndpointController.REQUEST_MAPPING + "/1")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    String endpointString = result.getResponse().getContentAsString();

    Endpoint returnedEndpoint = JsonUtility.loadObjectFromString(endpointString, Endpoint.class);
    Endpoint savedEndpoint = endpointService.findById(1L);
    assertEquals(returnedEndpoint, savedEndpoint);
  }

  // @Test
  public void testUpdateEndpoint() {
    fail("Not yet implemented");
  }

  // @Test
  public void testDeleteEndpoint() {
    fail("Not yet implemented");
  }

}
