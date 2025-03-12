package com.example.makerchecker.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class RefundServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("RefundServiceTask");
        // Logic to call payment service API for refund processing

        try {
            // Call payment service API to initiate refund
            PaymentService paymentService = new PaymentService();
            double amount  = (double) execution.getVariable("refundAmount");
            String paymentMethod = (String) execution.getVariable("paymentMethod");
            paymentService.initiateRefund(amount, paymentMethod);
            execution.setVariable("refundStatus", "SUCCESS");
            System.out.println("Refund initiated successfully");
        } catch (Exception e) {
            execution.setVariable("refundStatus", "FAILED");
            // Handle exception and log error
        }
    }


}
