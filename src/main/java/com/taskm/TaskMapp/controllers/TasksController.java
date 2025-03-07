package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.model.User;
import com.taskm.TaskMapp.repo.TaskRepository;
import com.taskm.TaskMapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Controller
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/tasks")
    public String tasks(Model model) {
        Iterable<Task> tasks = taskRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        return "tasks";
    }

    @PostMapping("/tasks/add")
    public String taskAdd(@RequestParam String title, String fullText, Model model, String assignee, String taskDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(taskDate);
        } catch (ParseException e) {
            System.out.println("Ошибка даты: " + e.getMessage());
        }

        Task task = new Task(title, fullText, assignee, date);
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/test")
    public String test(Model model) {

        return "test";
    }

    @PostMapping("/tasks/{id}/del")
    public String addPostDel(@PathVariable(value = "id") long id, Model model) {
        Task task = taskRepository.findById((int) id).orElseThrow();
        taskRepository.delete(task);

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String taskView(@PathVariable(value = "id") int id, Model model) {
        if (!taskRepository.existsById(id)) {
            return "redirect:/tasks";
        }
        ArrayList<Task> list = new ArrayList<>();
        Task task = taskRepository.findById(id).orElseThrow();
        list.add(task);
        model.addAttribute("task", list);
        return "task";
    }

    @GetMapping("tasks/{id}/edit")
    public String taskEdit(@PathVariable(value = "id") int id, Model model) {
        if (!taskRepository.existsById(id)) {
            return "redirect:/tasks";
        }
        Task task = taskRepository.findById(id).orElseThrow();
        ArrayList<Task> list = new ArrayList<>();
        list.add(task);
        model.addAttribute("task", list);

        return "task_edit";
    }

    @PostMapping("tasks/{id}/edit")
    public String taskEditPost(@PathVariable(value = "id") int id, Model model, String name, String fullText, String assignee, String date) {
        if (!taskRepository.existsById(id)) {
            return "redirect:/tasks";
        }
        Task task = taskRepository.findById(id).orElseThrow();
        task.setName(name);
        task.setDescription(fullText);
        task.setAssignee(assignee);
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
            System.out.println("Date error: " + e.getMessage());
        }
        task.setDueDate(newDate);
        taskRepository.save(task);

        return "redirect:/tasks";
    }
}
