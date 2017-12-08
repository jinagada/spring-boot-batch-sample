package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${smtp.host}")
    private String SMTP_HOST;
    @Value("${smtp.prot}")
    private int SMTP_PORT;
    @Value("${smtp.username}")
    private String SMTP_USER;
    @Value("${smtp.password}")
    private String SMTP_PW;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SMTP_HOST);
        mailSender.setPort(SMTP_PORT);
        mailSender.setDefaultEncoding("utf-8");
        mailSender.setUsername(SMTP_USER);
        mailSender.setPassword(SMTP_PW);
        // Gmail 설정
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
