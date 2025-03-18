package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.model.User;
import com.taskm.TaskMapp.repo.TaskRepository;
import com.taskm.TaskMapp.repo.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Controller
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TasksController.class);
    private static Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @GetMapping("/tasks")
    public String tasks(Model model) {
        Iterable<Task> tasks = taskRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        model.addAttribute("task", new Task());
        return "tasks";
    }

    @PostMapping("/tasks/add")
    public String taskAdd(@ModelAttribute("task") Task task) {
        logger.info("Cоздание задачи: {}", task.getName());

        taskRepository.save(task);

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Имя пользователя
            logger.info("Пользователь {} создал задачу: {}, ID: {}", username, task.getName(), task.getId());
        }
        return "redirect:/tasks";
    }

    @GetMapping("/test")
    public String test(Model model) {

        return "test";
    }

    @PostMapping("/tasks/{id}/del")
    public String addPostDel(@PathVariable(value = "id") long id, Model model) {
        Task task = taskRepository.findById((int) id).orElseThrow();
        logger.debug("Удаление задачи: {}", task.getName());
        taskRepository.delete(task);
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Имя пользователя
            logger.info("Пользователь {} удалил задачу: {}, ID: {}", username, task.getName(), task.getId());
        }

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String taskView(@PathVariable(value = "id") int id, Model model) {
        if (!taskRepository.existsById(id)) {
            return "redirect:/tasks";
        }
        ArrayList<Task> list = new ArrayList<>();
        Task task = taskRepository.findById(id).orElseThrow();
        Iterable<User> users = userRepository.findAll();
        list.add(task);
        model.addAttribute("users", users);
        model.addAttribute("task", list);
        model.addAttribute("taskEdit", task);
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
        model.addAttribute("task", task);

        return "task_edit";
    }

    @PostMapping("tasks/{id}/edit")
    @Transactional
    public String taskEditPost(@PathVariable int id, @ModelAttribute("task") @Valid Task task, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "redirect:/tasks/" + id;
        }
        // Получение информации о текущем пользователе
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Имя пользователя
            logger.info("Пользователь {} редактирует задачу: {}", username, task.getName());
        }

        if (!taskRepository.existsById(id)) {
            return "redirect:/tasks";
        }
        Task oldTask = taskRepository.findById(id).orElseThrow();
        oldTask.setName(task.getName());
        oldTask.setDescription(task.getDescription());
        oldTask.setDueDate(task.getDueDate());
        oldTask.setAssignee(task.getAssignee());

        taskRepository.save(oldTask);
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Имя пользователя
            logger.info("Пользователь {} отредактировал задачу: {}", username, task.getName());
        }

        return "redirect:/tasks";
    }
}
