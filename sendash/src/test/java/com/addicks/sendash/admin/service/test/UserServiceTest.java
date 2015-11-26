package com.addicks.sendash.admin.service.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.dao.jpa.UserRepository;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.UserService;
import com.addicks.sendash.admin.test.JsonUtility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Before
  public void initTests() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldSaveUser() throws JsonParseException, JsonMappingException, IOException {
    User user = JsonUtility.loadObjectFromJson(JsonUtility.USER_JSON, User.class);

    userService.save(user);

    verify(userRepository, times(1)).save(user);
  }
}
