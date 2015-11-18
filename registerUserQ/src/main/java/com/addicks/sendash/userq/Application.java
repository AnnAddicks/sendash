package com.addicks.sendash.userq;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.addicks.sendash.userq.mail.INewUserMailer;
import com.addicks.sendash.userq.mail.MailConfiguration;

@SpringBootApplication
@RabbitListener(queues = "userCreateQueue")
@Import(MailConfiguration.class)
@EnableScheduling
public class Application {
  @Autowired
  private INewUserMailer newUserMailer;

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  @Bean
  public Queue fooQueue() {
    return new Queue("userCreateQueue");
  }

  @RabbitHandler
  public void process(@Payload String message) {
    System.out.println(new Date() + ": " + message);
    String[] userFields = message.split(",");
    log.error("userFields: " + userFields);
    newUserMailer.welcomeNewUser(userFields[0], userFields[1]);
    log.error("Just welcomed our new user!");
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }

}
