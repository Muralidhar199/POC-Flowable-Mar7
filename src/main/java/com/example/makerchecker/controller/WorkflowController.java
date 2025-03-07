package com.example.makerchecker.controller;

import com.example.makerchecker.services.TaskRepresentation;
import com.example.makerchecker.services.WorkflowService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping("/process")
    public void startProcess() {
        workflowService.startProcess();
    }

    @GetMapping("/tasks")
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = workflowService.getTasks(assignee);
        // Convert tasks to TaskRepresentation DTOs
        return tasks.stream().map(task -> new TaskRepresentation(task.getId(), task.getName())).collect(Collectors.toList());
    }
}
