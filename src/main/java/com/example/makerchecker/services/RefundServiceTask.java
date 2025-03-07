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
        RefundRequest request = (RefundRequest) execution.getVariable("refundRequest");
        try {
            // Call payment service API to initiate refund
            PaymentService paymentService = new PaymentService();
            paymentService.initiateRefund(request.getRefundAmount(), request.getPaymentMethod());
            execution.setVariable("refundStatus", "SUCCESS");
            System.out.println("Refund initiated successfully");
        } catch (Exception e) {
            execution.setVariable("refundStatus", "FAILED");
            // Handle exception and log error
        }
    }


}
