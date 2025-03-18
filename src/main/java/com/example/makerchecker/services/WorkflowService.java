package com.example.makerchecker.services;


import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.ProcessInstanceHistoryLog;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskLogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkflowService  {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public List<TaskDTO> startProcess(Map<String, Object> refundRequest) {
        System.out.println("Starting the process");
        System.out.println("Refund Amount: " + refundRequest.get("refundAmount"));

//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        Map<String, Object> variables = new HashMap<>();
//
//        variables.put("status", "rejected");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("refundRequestProcess",refundRequest);
        System.out.println("Process started with id: " + processInstance.getId());
        //return getTaskHistory(processInstance.getId());

        return getUpcomingSteps(processInstance.getId(), "123");
    }


    public List<Task> getTasks(String taskName) {
        return taskService.createTaskQuery().taskName(taskName).list();
    }


 public ProcessInstanceHistoryLog getTaskHistory(String processInstanceId) {
     ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

     ProcessInstanceHistoryLog log1 = processEngine.getHistoryService().createProcessInstanceHistoryLogQuery(processInstanceId).singleResult();

     System.out.println(log1.getStartTime());
     System.out.println(log1.getEndTime());
      return  log1;


//     return processEngine.getHistoryService().createHistoricTaskLogEntryQuery().taskId("ApprovalTask").list();
//     ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//     List<HistoricTaskInstance> nativeTasks = processEngine.getHistoryService().createNativeHistoricTaskInstanceQuery()
//             .sql("SELECT * FROM ACT_HI_TASKINST WHERE PROC_INST_ID_ = #{processInstanceId}")
//             .parameter("processInstanceId", processInstanceId)
//             .list();
//
//     System.out.println("Number of tasks: " + nativeTasks.size());
//
//     for (HistoricTaskInstance task : nativeTasks) {
//         System.out.println("Task ID: " + task.getId());
//         System.out.println("Task Name: " + task.getName());
//     }


//     ProcessInstanceHistoryLog log = processEngine.getHistoryService().createProcessInstanceHistoryLogQuery("refundRequestProcess")
//             .includeTasks()
//             .includeVariables()
//             .singleResult();
//      if(null != log)
//     System.out.println("Task ID: " + log.getProcessDefinitionId());
     //}

//    public List<org.flowable.engine.history.HistoricActivityInstance> getProcessHistory(String processInstanceId) {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        return processEngine.getHistoryService()
//                .createHistoricActivityInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .orderByHistoricActivityInstanceStartTime()
//                .asc()
//                .list();
//    }
 //    return nativeTasks;
 }

        public List<HistoricActivityInstance> getTransitionHistory(String processInstanceId) {
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            HistoryService historyService = processEngine.getHistoryService();
    // Query historic activity instances for the process instance
    List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
            .processInstanceId(processInstanceId)
            .orderByHistoricActivityInstanceStartTime()
            .asc()
            .list();

    // Print state transitions
        for (HistoricActivityInstance activity : activityInstances) {
        System.out.println("State: " + activity.getActivityName());
        System.out.println("Type: " + activity.getActivityType());
        System.out.println("Start Time: " + activity.getStartTime());
        System.out.println("End Time: " + activity.getEndTime());
        System.out.println("----------------------------");
    }
            return activityInstances;
        }


    public List<TaskDTO> getUpcomingSteps(String refundId, String userId) {
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//                .processInstanceBusinessKey(refundId)
//                .singleResult();

        String currentRole = userId.equals("123") ? "Maker" : "Checker";  //(String) runtimeService.getVariable(processInstance.getId(), "currentRole");
        String makerUserId = (String) runtimeService.getVariable(refundId, "makerUserId");
        String checkerUserId = (String) runtimeService.getVariable(refundId, "checkerUserId");

        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(refundId)
                .list();

        return tasks.stream()
                .filter(task -> isTaskEligibleForUser(task, currentRole, userId, makerUserId, checkerUserId))
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO convertToTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setAssignee(task.getAssignee());
        dto.setOwner(task.getOwner());
        dto.setCreateTime(task.getCreateTime());
        dto.setDueDate(task.getDueDate());
        dto.setPriority(task.getPriority());
        dto.setProcessInstanceId(task.getProcessInstanceId());
        dto.setProcessDefinitionId(task.getProcessDefinitionId());
        dto.setTaskDefinitionKey(task.getTaskDefinitionKey());
        dto.setFormKey(task.getFormKey());

        // Fetch and set task variables
        Map<String, Object> variables = taskService.getVariables(task.getId());
        dto.setVariables(variables);

        // Set candidate users and groups
        List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
        StringBuilder candidateUsers = new StringBuilder();
        StringBuilder candidateGroups = new StringBuilder();
        for (IdentityLink link : identityLinks) {
            if (link.getUserId() != null) {
                candidateUsers.append(link.getUserId()).append(",");
            }
            if (link.getGroupId() != null) {
                candidateGroups.append(link.getGroupId()).append(",");
            }
        }
        dto.setCandidateUsers(candidateUsers.toString());
        dto.setCandidateGroups(candidateGroups.toString());

        return dto;
    }


    private boolean isTaskEligibleForUser(Task task, String currentRole, String userId, String makerUserId, String checkerUserId) {
        if ("MAKER".equals(currentRole) && userId.equals(makerUserId)) {
            return task.getTaskDefinitionKey().equals("createRefundRequest") ||
                    task.getTaskDefinitionKey().equals("cancelRefund");
        } else if ("CHECKER".equals(currentRole) && userId.equals(checkerUserId)) {
            return task.getTaskDefinitionKey().equals("reviewRefund") ||
                    task.getTaskDefinitionKey().equals("approveRefund") ||
                    task.getTaskDefinitionKey().equals("rejectRefund");
        }
        return false;
    }


}
