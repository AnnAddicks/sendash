package com.addicks.sendash.userq.mail;

import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService {

  private final Logger log = LoggerFactory.getLogger(MailService.class);

  private static final String FROM = "support@sendash.com";

  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public void sendEmail(String to, String subject, String content, boolean isMultipart,
      boolean isHtml) {
    log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
        isMultipart, isHtml, to, subject, content);

    // Prepare message using a Spring helper
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart,
          CharEncoding.UTF_8);
      message.setTo(to);
      message.setFrom(FROM);
      message.setSubject(subject);
      message.setText(content, isHtml);
      javaMailSender.send(mimeMessage);
      log.debug("Sent e-mail to User '{}'", to);
    }
    catch (Exception e) {
      log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
    }
  }
}
