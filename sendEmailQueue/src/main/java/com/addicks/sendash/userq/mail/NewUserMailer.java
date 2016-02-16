package com.addicks.sendash.userq.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class NewUserMailer implements INewUserMailer {

  private Configuration configuration;

  private IMailService mailService;

  private static final Logger log = LoggerFactory.getLogger(NewUserMailer.class);

  private static final String CONFIRM_URI = "www.sendash.com/api/register/user/";

  private static final String WELCOME_TEMPLATE = "Registration.ftl";

  @Autowired
  public NewUserMailer(Configuration configuration, IMailService mailService) {

    this.configuration = configuration;
    this.mailService = mailService;
  }

  @Override
  public void welcomeNewUser(String email, String uuidURI) {

    Map<String, String> data = new HashMap<>();
    data.put("email", email);
    data.put("uri", CONFIRM_URI + uuidURI);
    String subject = "Welcome to Sendash!";
    try {
      Template template = configuration.getTemplate(WELCOME_TEMPLATE);
      String emailText = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
      mailService.sendEmail(email, subject, emailText, false, true);
    }
    catch (MailException | IOException | TemplateException e) {
      log.error("Failed to send an email.", e);
    }
  }
}
