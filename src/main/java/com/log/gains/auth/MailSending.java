package com.log.gains.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailSending {
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("loggainsbusiness@gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("loggainsbusiness@gmail.com");
        mailSender.setPassword("LogGainsBusiness420");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        // Other properties can be set here

        return mailSender;
    }

}
