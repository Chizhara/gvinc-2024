package com.gnivc.portalservice;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class Testtttt {
    public static void main(String[] args) {
        sendPassword("federico.chizhara666@yandex.ru", "234sdf2");
    }
    public static void sendPassword(String recipientMail, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.yandex.ru");
        mailSender.setPort(465);

        mailSender.setUsername("federico.chizhara@yandex.ru");
        mailSender.setPassword("uzqzdepjjmzvyqjg");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "*");
        mailSender.setJavaMailProperties(props);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("federico.chizhara@yandex.ru");
        message.setTo(recipientMail);
        message.setSubject("Registation");
        message.setText(password);
        mailSender.send(message);
    }
}
