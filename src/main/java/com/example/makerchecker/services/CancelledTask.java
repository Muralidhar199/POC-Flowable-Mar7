package com.example.makerchecker.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class CancelledTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Cancelled Task");
        new PendingApproval().listOfPendingApprovals(execution);
    }
}
