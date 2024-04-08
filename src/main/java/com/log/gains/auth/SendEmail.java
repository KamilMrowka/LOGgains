package com.log.gains.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmail {
    private MailSending mailSending;
    private JavaMailSender mailSender;

    public void sendEmail(String recipient, String subject, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText("Siemano " + name + ", witam Cię na mojej stronie, a teraz zapierdalaj na siłownię. Twój kod weryfikacyjny to 1234 wpisz se go kurwa na czoło.");

        mailSender.send(message);
    }

}
//C:\Users\kamil\IdeaProjects\LOGgains\src\main\java\com\log\gains\auth\SendEmail.java:18:19
//java: cannot access jakarta.mail.internet.MimeMessage
//  class file for jakarta.mail.internet.MimeMessage not found
// Why do I need the MimeMessage class?
