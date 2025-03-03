package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public String tasks(Model model) {
        Iterable<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/tasks/add")
    public String taskAdd(@RequestParam String title, String fullText, Model model) {

        System.out.println("In tasks add");
        Task task = new Task(title, fullText);
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/test")
    public String test(Model model) {

        return "test";
    }

    @PostMapping("/tasks/{id}/del")
    public String addPostDel(@PathVariable(value = "id") long id, Model model) {
        Task task = taskRepository.findById((int)id).orElseThrow();
        taskRepository.delete(task);

        return "redirect:/tasks";
    }


}
