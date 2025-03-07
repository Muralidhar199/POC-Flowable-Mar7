package com.example.makerchecker.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String recipient, String subject, String content) {
        // Simulate sending an email
        System.out.println("Sending email to " + recipient + " with subject: " + subject);
    }
}
