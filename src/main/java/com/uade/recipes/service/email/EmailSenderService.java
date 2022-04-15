package com.uade.recipes.service.email;

public interface EmailSenderService {

    void sendSimpleEmail(String toEmail,
                         String body,
                         String subject);
}
