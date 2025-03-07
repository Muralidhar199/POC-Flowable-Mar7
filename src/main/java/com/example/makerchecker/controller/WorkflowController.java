package com.example.makerchecker.controller;

import com.example.makerchecker.services.TaskRepresentation;
import com.example.makerchecker.services.WorkflowService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping("/process")
    public String startProcess() {
       return workflowService.startProcess();
    }

    @GetMapping("/tasks")
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = workflowService.getTasks(assignee);
        // Convert tasks to TaskRepresentation DTOs
        return tasks.stream().map(task -> new TaskRepresentation(task.getId(), task.getName())).collect(Collectors.toList());
    }
}
