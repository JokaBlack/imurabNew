package com.attractorschool.imurab.service.mail;

import java.util.Map;

public interface MailService {
    void sendHtml(String body, Map<String, Object> variables);
}
