package com.addicks.sendash.admin.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.addicks.sendash.admin.api.rest.FileController;

public class FileControllerTest extends ControllerTest {

  @InjectMocks
  private FileController controller;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void initTests() throws IOException {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void shouldReturnZip() throws Exception {
    mvc.perform(get(SERVER + FileController.REQUEST_MAPPING + "/zip")
        .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE)).andExpect(status().isOk())
        .andExpect(content().contentType("application/zip")).andDo(MockMvcResultHandlers.print());
  }
}
