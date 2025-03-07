package com.taskm.TaskMapp.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String assignee;
    private Date dueDate;


    public Task(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Task(String name, String description, String assignee) {
        this.name = name;
        this.description = description;
        this.assignee = assignee;

    }

    public Task() {

    }

    public Task(String name, String description, String assignee, Date dueDate) {
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.dueDate = dueDate;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDueDate() {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = outputFormat.format(dueDate);


        return formattedDate;
    }

    public Date getDate() {


        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
