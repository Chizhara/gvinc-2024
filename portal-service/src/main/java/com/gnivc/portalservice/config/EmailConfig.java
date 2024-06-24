package com.gnivc.portalservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.smtp.host}")
    private String host;
    @Value("${spring.mail.smtp.port}")
    private int port;
    @Value("${spring.mail.user}")
    private String user;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.smtp.ssl.enable}")
    private boolean enable;
    @Value("${spring.mail.smtp.auth}")
    private boolean auth;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(user);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enable);
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "*");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
