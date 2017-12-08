package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AlramService {
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${batcherror.send.email}")
    private String senderEmail;

    public boolean alramSend(final String receive, final String alramTitle, final String alramMessage, final String
            template) throws
            Exception {
        boolean isSended = false;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "UTF-8");
        messageHelper.setTo(receive);
        messageHelper.setFrom(senderEmail);
        messageHelper.setSubject(alramTitle);
        Map<String, Object> param = new HashMap<>();
        param.put("messageStr", alramMessage);
        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "utf-8", param);
        messageHelper.setText(content);
        mailSender.send(message);
        isSended = true;
        return isSended;
    }
}
