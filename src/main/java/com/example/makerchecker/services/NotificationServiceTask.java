package com.example.makerchecker.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Sending notification to user");
        // Generate notification content using a template engine
        String content = generateNotificationContent((RefundRequest) execution.getVariable("refundRequest"));
        System.out.println("Notification content: " + content);
        // Send notification via email or SMS
        EmailService emailService = new EmailService();
        System.out.println("Sending email to " + "muralidhar@gmail.com");
        emailService.sendEmail("muralidhar@gmail.com", "Refund Notification", content);
    }

    private String generateNotificationContent(RefundRequest request) {
        // Implement logic to generate notification content
        return "Your refund has been processed successfully.";
    }
}
