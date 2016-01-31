package com.addicks.sendash.admin.api.rest.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

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
import com.addicks.sendash.admin.test.InputStreamToFile;
import com.addicks.sendash.admin.test.ZipCompare;

public class FileControllerTest extends ControllerTest {

  @SuppressWarnings("unused")
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

    byte[] zip = result.getResponse().getContentAsByteArray();
    File zipFromController = InputStreamToFile.convert(new ByteArrayInputStream(zip), ".zip");
    File zipFromFile = fileService.getZipFile();

    ZipFile fromController = new ZipFile(zipFromController);
    ZipFile fromFile = new ZipFile(zipFromFile);
    assertTrue(ZipCompare.filesEqual(fromController, fromFile));

    zipFromController.delete();
    fromController.close();
    fromFile.close();

  }
}
