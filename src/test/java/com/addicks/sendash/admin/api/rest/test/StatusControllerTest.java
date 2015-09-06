package com.addicks.sendash.admin.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

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

import com.addicks.sendash.admin.api.rest.StatusController;
import com.addicks.sendash.admin.domain.Script;
import com.addicks.sendash.admin.domain.properties.RepositoryProperties;
import com.addicks.sendash.admin.service.GitService;
import com.addicks.sendash.admin.service.ScriptService;

public class StatusControllerTest extends ControllerTest {

  private static final String TEST_URI = "http://localhost/status";

  private static final String TEST_URI_STUB = "http://localhost/status/stub";

  @InjectMocks
  private StatusController controller;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ScriptService scriptService;

  @Autowired
  private GitService gitService;

  @Autowired
  private RepositoryProperties repositoryProperties;

  private MockMvc mvc;

  private String workerFile;

  private static final String FIRST_POWERSHELL_FILE = "/Worker.ps1";

  @Before
  public void initTests() throws IOException {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    workerFile = readWorkerFile();

    gitService.updateLocalRepository();
  }

  private String readWorkerFile() throws IOException {
    String workerString = new String(
        Files.readAllBytes(Paths.get(repositoryProperties.getLocalRepo() + FIRST_POWERSHELL_FILE)),
        Charset.forName("UTF-8"));

    if (workerString.startsWith("\ufeff")) {
      workerString = workerString.substring(1);
    }

    return workerString;
  }

  @Test
  public void shouldCreateDefaultStatusWithUpdateNeeded() throws Exception {
    mvc.perform(get(TEST_URI_STUB).header("API_KEY", "update").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.updateNeeded").value(Boolean.TRUE))
        .andExpect(jsonPath("$.healthCheckCronSchedule").value(" 0 08 * * * "))
        .andExpect(jsonPath("$.data").value("get-process")).andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void shouldCreateDefaultStatusWithNoUpdateNeeded() throws Exception {
    mvc.perform(get(TEST_URI_STUB).header("API_KEY", "asdf").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.updateNeeded").value(Boolean.FALSE))
        .andExpect(jsonPath("$.healthCheckCronSchedule").value(" 0 08 * * * "))
        .andExpect(jsonPath("$.data").value("get-process")).andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void ShouldCreateStatusReadingDataFromAFile() throws Exception {
    Script script = new Script();
    script.setData("test data");
    script.setScriptLastUpdated(Calendar.getInstance().getTime());
    script.setScriptName("worker script 2");
    scriptService.save(script);

    mvc.perform(post(TEST_URI).contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\": \"zzfe6e4c-f45a-43ef-8ca7-d2219b509099\",\"apiKey\": \"H9cZ0GA5WgaP\""
            + ",\"lastUpdatedScripts\": 1185937200000}")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updateNeeded").value(Boolean.TRUE))
        .andExpect(jsonPath("$.data").value(workerFile)).andDo(MockMvcResultHandlers.print());
  }

}
