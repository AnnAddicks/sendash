package com.addicks.sendash.admin.service.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.domain.PendingEndpoint;
import com.addicks.sendash.admin.service.IPendingEndpointService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
@Profile("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    FlywayTestExecutionListener.class })
@FlywayTest
public class PendingEndpointServiceTest {

  private static final Logger log = LoggerFactory.getLogger(PendingEndpointServiceTest.class);

  @Autowired
  private IPendingEndpointService service;

  @Test
  public void testApprove() throws JsonParseException, JsonMappingException, IOException {
    Page<PendingEndpoint> pendingEndpoints = service.findAll(0, 100);

    Collection<Long> ids = new ArrayList<>();
    for (PendingEndpoint endpoint : pendingEndpoints.getContent()) {
      ids.add(endpoint.getId());
    }

    service.approve(ids);

    pendingEndpoints = service.findAll(0, 100);
    assertThat(pendingEndpoints.getContent(), empty());
  }

}
