package com.addicks.sendash.userq.mail;

public interface IMailService {

  void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);
}
