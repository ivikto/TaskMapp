package com.taskm.TaskMapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private final TaskRepository taskRepository;

    public MainController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


    @GetMapping("/calendar")
    public String calendar(Model model) {
        Iterable<Task> tasks = taskRepository.findAll();

        // Преобразуем задачи в нужный формат
        List<Map<String, Object>> formattedTasks = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("title", task.getName());
            taskMap.put("start", task.getDueDate().toString()); // Преобразуем LocalDate в строку
            taskMap.put("description", task.getDescription());
            taskMap.put("assignee", task.getAssignee());
            formattedTasks.add(taskMap);
        }

        // Настройка ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Подключаем модуль для Java 8 date/time

        String tasksJson = null; // Преобразуем задачи в JSON
        try {
            tasksJson = objectMapper.writeValueAsString(formattedTasks);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(tasksJson); // Выводим JSON в консоль для отладки
        model.addAttribute("tasksJson", tasksJson); // Передаем JSON в модель
        return "calendar";
    }

    @GetMapping("/error")
    public String error(String error, Model model) {

        return "error";
    }






}
