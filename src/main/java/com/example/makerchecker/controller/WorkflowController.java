package com.example.makerchecker.controller;

import com.example.makerchecker.services.RefundRequest;
//import com.example.makerchecker.services.TaskRepresentation;
import com.example.makerchecker.services.TaskDTO;
import com.example.makerchecker.services.TaskRepresentation;
import com.example.makerchecker.services.WorkflowService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.history.ProcessInstanceHistoryLog;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskLogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping("/process")
    public List<TaskDTO> startProcess(@RequestBody Map<String, Object> refundRequest) {

       return workflowService.startProcess(refundRequest);
    }

    @GetMapping("/tasks")
    public List<TaskRepresentation> getTasks() {
        List<Task> tasks = workflowService.getTasks("Rejected Task");
        // Convert tasks to TaskRepresentation DTOs
        return tasks.stream().map(task -> new TaskRepresentation(task.getId(), task.getName())).collect(Collectors.toList());
    }


    @GetMapping("/taskHistory/{processInstanceId}")
    public void getTaskHistory(@PathVariable String processInstanceId) {
         workflowService.getTransitionHistory(processInstanceId);
    }



    @GetMapping("/nextsteps/{processInstanceId}/{userId}")
    public List<TaskDTO> getNextSteps(@PathVariable String processInstanceId, @PathVariable String userId) {
        return workflowService.getUpcomingSteps(processInstanceId,userId);
    }
}
