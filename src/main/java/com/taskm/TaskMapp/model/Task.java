package com.taskm.TaskMapp.model;

import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dueDate;


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

    public Task(String name, String description, String assignee, LocalDate dueDate) {
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getDate() {


        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", assignee='" + assignee + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}

