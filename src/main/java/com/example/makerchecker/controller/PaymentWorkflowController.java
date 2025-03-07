//package com.example.makerchecker.controller;
//
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/payments")
//public class PaymentWorkflowController {
//
//    @Autowired
//    private RuntimeService runtimeService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @GetMapping("/Test")
//    public String getMessage() {
//    	return "Success Message";
//    }
//
//    @PostMapping("/initiate")
//    public String initiatePayment(@RequestBody Map<String, Object> paymentDetails) {
//        runtimeService.startProcessInstanceByKey("paymentWorkflow", paymentDetails);
//        return "Payment request initiated!";
//    }
//
//    @PostMapping("/review/{taskId}")
//    public String reviewPayment(@PathVariable String taskId, @RequestParam boolean isValid) {
//        taskService.complete(taskId, Map.of("isValid", isValid));
//        return "Payment review completed!";
//    }
//
//    @PostMapping("/approve/{taskId}")
//    public String approvePayment(@PathVariable String taskId, @RequestParam boolean isApproved) {
//        taskService.complete(taskId, Map.of("isApproved", isApproved));
//        return "Payment approval completed!";
//    }
//}
//
