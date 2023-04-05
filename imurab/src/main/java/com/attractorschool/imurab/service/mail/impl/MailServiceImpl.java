package com.attractorschool.imurab.service.mail.impl;

import com.attractorschool.imurab.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;
    private final TemplateEngine engine;

    @Override
    public void sendHtml(String body, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String htmlBody = engine.process(String.format("/mail/%s.html", body), context);

        try {
            send(from, htmlBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void send(String to, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(body, true);
        mailSender.send(message);
    }
}
