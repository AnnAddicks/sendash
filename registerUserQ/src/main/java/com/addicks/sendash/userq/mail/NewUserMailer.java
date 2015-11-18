package com.addicks.sendash.userq.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class NewUserMailer implements INewUserMailer {

  @Autowired
  private JavaMailSender javaMailService;

  @Autowired
  private Configuration configuration;

  private static final Logger log = LoggerFactory.getLogger(NewUserMailer.class);

  private static final String CONFIRM_URI = "www.sendash.com/register/user/";

  private static final String WELCOME_TEMPLATE = "Registration.ftl";

  @Override
  public void welcomeNewUser(String email, String uuidURI) {
    log.error("**********************");
    log.error("Welcome " + email);
    Map<String, String> data = new HashMap<>();
    data.put("email", email);
    data.put("uri", CONFIRM_URI + uuidURI);

    try {
      Template template = configuration.getTemplate(WELCOME_TEMPLATE);
      String emailText = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

      log.error("emailText");
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(email);
      mailMessage.setSubject("Welcome to Sendash!");
      mailMessage.setText(emailText);

      javaMailService.send(mailMessage);
      log.error("**********************");
    }
    catch (MailException | IOException | TemplateException e) {
      log.error("Failed to send an email.", e);
    }
  }
}
