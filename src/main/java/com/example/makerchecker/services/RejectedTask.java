package com.example.makerchecker.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class RejectedTask implements JavaDelegate {



    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Rejected Task");
        new PendingApproval().listOfPendingApprovals(execution);

    }
}
