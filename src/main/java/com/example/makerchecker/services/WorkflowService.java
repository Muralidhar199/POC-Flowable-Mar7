package com.example.makerchecker.services;


import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkflowService  {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public String startProcess(Map<String, Object> refundRequest) {
        System.out.println("Starting the process");
        System.out.println("Refund Amount: " + refundRequest.get("refundAmount"));

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("refundRequestProcess",refundRequest);
        System.out.println("Process started with id: " + processInstance.getId());
        return processInstance.getId();
    }


    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }


}
