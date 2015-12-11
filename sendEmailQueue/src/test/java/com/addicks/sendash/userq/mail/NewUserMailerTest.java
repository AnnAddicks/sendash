package com.addicks.sendash.userq.mail;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.addicks.sendash.userq.Application;

import freemarker.template.Configuration;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class NewUserMailerTest {

  @Mock
  private JavaMailSender javaMailService;

  @Autowired
  private Configuration configuration;

  private NewUserMailer newUserMailer;

  @Test
  public void testWelcomeNewUser() {
    newUserMailer = new NewUserMailer(javaMailService, configuration);
    String email = "test@test.com";
    String uuid = UUID.randomUUID().toString();

    newUserMailer.welcomeNewUser(email, uuid);

    verify(javaMailService).send(any());
  }

}
