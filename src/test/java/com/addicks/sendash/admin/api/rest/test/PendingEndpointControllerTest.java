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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.addicks.sendash.admin.api.rest.PendingEndpointController;
import com.addicks.sendash.admin.api.rest.UserController;
import com.addicks.sendash.admin.domain.PendingEndpoint;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.IPendingEndpointService;
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
public class PendingEndpointControllerTest extends ControllerTest {
  private static final Logger log = LoggerFactory.getLogger(PendingEndpointControllerTest.class);

  @InjectMocks
  private UserController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private IPendingEndpointService pendingEndpointService;

  private MockMvc mvc;

  private byte[] pendingEndpointJson;

  @Before
  public void initTests() throws JsonParseException, JsonMappingException, IOException, Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    PendingEndpoint pendingEndpoint = JsonUtility
        .loadObjectFromJson(JsonUtility.PENDING_ENPOINT_JSON, PendingEndpoint.class);
    pendingEndpointJson = toJson(pendingEndpoint);
  }

  @Test
  public void testCreatependingEndpoint() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetAllPendingEndpoints() throws Exception {
    User user = new User();
    user.setId(1L);

    MvcResult result = mvc
        .perform(
            get(PendingEndpointController.REQUEST_MAPPING).contentType(MediaType.APPLICATION_JSON)
                .principal(getPrincipal()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    String pendingEndpointString = result.getResponse().getContentAsString();

    List<PendingEndpoint> returnedPendingEndpoint = JsonUtility
        .loadObjectListFromString(pendingEndpointString, PendingEndpoint.class);
    Page<PendingEndpoint> savedPendingEndpoints = pendingEndpointService.findAll(user, 0, 50);

    log.error("returnedPendingEndpoint: " + returnedPendingEndpoint);
    log.error("savedPendingEndpoints: " + savedPendingEndpoints);
    log.error("findAll:" + pendingEndpointService.findAll(0, 50));
    assertEquals(returnedPendingEndpoint, savedPendingEndpoints.getContent());
  }

  @Test
  public void testGetpendingEndpoint() {
    fail("Not yet implemented");
  }

  @Test
  public void testUpdatependingEndpoint() {
    fail("Not yet implemented");
  }

  @Test
  public void testDeletePendingEndpoint() {
    fail("Not yet implemented");
  }

  @Test
  public void testApprovePendingEndpoint() {
    fail("Not yet implemented");
  }

  @Test
  public void testRejectPendingEndpoint() {
    fail("Not yet implemented");
  }

}
