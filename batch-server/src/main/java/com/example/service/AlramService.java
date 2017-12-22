package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class AlramService {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${batcherror.send.email}")
    private String senderEmail;

    public boolean alramSend(final String receive, final String alramTitle, final String alramMessage, final String templatePath) throws Exception {
        boolean isSended = false;
        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "UTF-8");
        messageHelper.setTo(receive);
        messageHelper.setFrom(senderEmail);
        messageHelper.setSubject(alramTitle);
        final Context context = new Context();
        context.setVariable("messageStr", alramMessage);
        final String content = templateEngine.process(templatePath, context);
        messageHelper.setText(content);
        mailSender.send(message);
        isSended = true;
        return isSended;
    }
}
