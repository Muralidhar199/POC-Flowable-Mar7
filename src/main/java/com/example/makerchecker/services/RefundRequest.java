package com.example.makerchecker.services;

import org.springframework.stereotype.Component;


public class RefundRequest {

    private String id;
    private double refundAmount;
    private String paymentMethod;
    private String userId;
    private String reason;

    // Constructor
    public RefundRequest(String id, double refundAmount, String paymentMethod, String userId, String reason) {
        this.id = id;
        this.refundAmount = refundAmount;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.reason = reason;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RefundRequest{" +
                "id='" + id + '\'' +
                ", refundAmount=" + refundAmount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", userId='" + userId + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
