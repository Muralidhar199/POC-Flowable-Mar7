package com.example.makerchecker.services;


import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public String startProcess() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("refundRequestProcess");
        System.out.println("Process started with id: " + processInstance.getId());
        return processInstance.getId();
    }


    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }
}
