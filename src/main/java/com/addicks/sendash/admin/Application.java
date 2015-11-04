package com.addicks.sendash.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mangofactory.swagger.plugin.EnableSwagger;

/*
 * This is the main Spring Boot application class. It configures Spring Boot, JPA, Swagger
 */

@EnableAutoConfiguration // Sprint Boot Auto Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.addicks.sendash.admin")
// To segregate MongoDB and JPA repositories. Otherwise not needed.
@EnableJpaRepositories("com.addicks.sendash.admin.dao.jpa")
@EnableSwagger // auto generation of API docs
public class Application extends SpringBootServletInitializer {

  private static final Class<Application> applicationClass = Application.class;

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(applicationClass);

  public static void main(String[] args) {
    SpringApplication.run(applicationClass, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

}
