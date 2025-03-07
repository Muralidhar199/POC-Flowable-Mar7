package com.example.makerchecker.services;


import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void initiateRefund(double amount, String paymentMethod) {
        // Simulate payment service API call
        System.out.println("Initiating refund of " + amount + " via " + paymentMethod);
    }
}
