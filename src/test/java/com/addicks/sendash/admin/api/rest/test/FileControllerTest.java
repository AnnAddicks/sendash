package com.addicks.sendash.admin.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.addicks.sendash.admin.api.rest.FileController;
import com.addicks.sendash.admin.api.rest.GithubHookController;
import com.addicks.sendash.admin.service.FileService;

public class FileControllerTest extends ControllerTest {

  private static final Logger log = LoggerFactory.getLogger(GithubHookController.class);

  @InjectMocks
  private FileController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private FileService fileService;

  private MockMvc mvc;

  @Before
  public void initTests() throws IOException {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void shouldReturnZip() throws Exception {
    MvcResult result = mvc
        .perform(get(SERVER + FileController.REQUEST_MAPPING + "/zip").accept("application/zip"))
        .andExpect(status().isOk()).andExpect(content().contentType("application/zip"))
        .andDo(MockMvcResultHandlers.print()).andReturn();

    log.error("result: " + result.getResponse().getContentAsByteArray());
    log.error("String " + new String(result.getResponse().getContentAsByteArray()));

  }
}
