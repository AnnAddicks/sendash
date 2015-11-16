package com.addicks.sendash.admin.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ActiveProfiles;
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

	
	public void send() {
		this.rabbitTemplate.convertAndSend("foo", "hello");
	}
	
	@Test
	public void test() {
		do{
		send();
		} while(true);
	}

}
