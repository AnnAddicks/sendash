package com.addicks.sendash.userq.mail;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.addicks.sendash.userq.Application;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class NewUserMailerTest {

  @Autowired
  private MailConfiguration mailConf;

  @Autowired
  private freemarker.template.Configuration configuration;

  @Autowired
  private IMailService mailService;

  private NewUserMailer newUserMailer;

  @Configuration
  public static class Config {
    @Bean
    JavaMailSender javaMailSender() {
      return Mockito.mock(JavaMailSender.class);
    }
  }

  @Test
  public void testWelcomeNewUser() {

    newUserMailer = new NewUserMailer(configuration, mailService);
    String email = "test@test.com";
    String uuid = UUID.randomUUID().toString();

    // newUserMailer.welcomeNewUser(email, uuid);

  }

}
