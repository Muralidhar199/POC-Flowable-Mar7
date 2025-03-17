package com.example.makerchecker.services;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingApproval {

    public void listOfPendingApprovals(DelegateExecution execution) {
         System.out.println("Checking pending approval status");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> pendingTasks = taskService.createTaskQuery()
                .taskDefinitionKey("refundRequestProcess").list();

        for (Task task : pendingTasks) {
            System.out.println("Task ID: " + execution.getVariable("id"));
            System.out.println("Task Name: " + execution.getVariable("userId"));
        }

        if (pendingTasks.isEmpty()) {
            System.out.println("No pending approvals found");
        }
    }
}
