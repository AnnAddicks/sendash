package com.addicks.sendash.admin.api.rest.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.regex.Pattern;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.dao.jpa.UserRepository;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.CustomUserDetailsService.UserRepositoryUserDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Profile("test")
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public abstract class ControllerTest {

  protected static String SERVER = "http://localhost";

  private static final Logger log = LoggerFactory.getLogger(ControllerTest.class);

  @Autowired
  private UserRepository userRepository;

  // match redirect header URL (aka Location header)
  protected static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
    return new ResultMatcher() {
      @Override
      public void match(MvcResult result) {

        log.error("result" + result.getResponse().getRedirectedUrl());
        Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
        assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
      }
    };
  }

  protected long getResourceIdFromUrl(String locationUrl) {
    String[] parts = locationUrl.split("/");
    return Long.valueOf(parts[parts.length - 1]);
  }

  protected byte[] readObjectFromJsonFile(String fileName, Class<?> clazz)
      throws IOException, JsonParseException, JsonMappingException, Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    String path = file.getPath();

    ObjectMapper mapper = new ObjectMapper();
    Object jsonObject = mapper.readValue(new File(path), clazz);
    byte[] json = toJson(jsonObject);
    return json;
  }

  protected byte[] toJson(Object r) throws Exception {
    ObjectMapper map = new ObjectMapper();
    return map.writeValueAsString(r).getBytes();
  }

  protected Principal getPrincipal() {
    User user = userRepository.findByEmail("test@test.com");
    TestUserDetails userDetails = new TestUserDetails(user);

    TestingAuthenticationToken token = new TestingAuthenticationToken(userDetails, null);
    return new OAuth2Authentication(null, token);

  }

  @SuppressWarnings("serial")
  private class TestUserDetails extends UserRepositoryUserDetails {

    public TestUserDetails(User user) {
      super(user);
    }

  }
}
