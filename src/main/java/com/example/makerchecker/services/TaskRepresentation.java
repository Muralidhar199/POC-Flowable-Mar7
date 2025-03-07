package com.example.makerchecker.services;

import org.springframework.stereotype.Component;

@Component
public class TaskRepresentation {

    private String id;
    private String name;
    private String assignee;
    private String description;
    private String status;

    public TaskRepresentation(){

    }

    // Constructor
    public TaskRepresentation(String id, String name, String assignee) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
    }

    public TaskRepresentation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskRepresentation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", assignee='" + assignee + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
