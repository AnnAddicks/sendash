package com.addicks.sendash.admin.test;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.addicks.sendash.admin.Application;

@Profile("MicroserviceIntegrationTests")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class TestQ {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  // Read in the os environment variable
  @Value("${user.create.queue}")
  private String userQueue;

  public void send() {
    this.rabbitTemplate.convertAndSend(userQueue, "test@test.com," + UUID.randomUUID());
  }

  @Test
  public void test() {
    this.send();
  }

}
