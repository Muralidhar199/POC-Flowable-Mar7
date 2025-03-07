package com.example.makerchecker.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        // Generate notification content using a template engine
        String content = generateNotificationContent((RefundRequest) execution.getVariable("refundRequest"));
        // Send notification via email or SMS
        EmailService emailService = new EmailService();
        emailService.sendEmail("user@example.com", "Refund Notification", content);
    }

    private String generateNotificationContent(RefundRequest request) {
        // Implement logic to generate notification content
        return "Your refund has been processed successfully.";
    }
}
