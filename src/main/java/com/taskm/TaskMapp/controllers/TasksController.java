package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.Bot;
import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.model.User;
import com.taskm.TaskMapp.repo.TaskRepository;
import com.taskm.TaskMapp.repo.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.Set;

@Controller
public class TasksController {

    private static final String redirect = "redirect:/tasks";

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TasksController.class);
    private static final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

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
    public String taskAdd(@ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) {
        logger.info("Cоздание задачи: {}", task.getName());

        try {
            taskRepository.save(task);
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            StringBuilder errorMessage = new StringBuilder("Ошибка валидации: ");

            // Собираем все сообщения об ошибках
            for (ConstraintViolation<?> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }

            // Добавляем сообщение об ошибке в RedirectAttributes
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage.toString());
            return "redirect:/error";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании задачи: " + e.getMessage());
            return "redirect:/error";
        }

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Имя пользователя
            logger.info("Пользователь {} создал задачу: {}, ID: {}", username, task.getName(), task.getId());
        }
        String message = String.format("Создана новая задача: %s, Исполнитель: %s", task.getName(), task.getAssignee());
        Bot.sendMessage(message);
        return redirect;
    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }

    @PostMapping("/tasks/{id}/del")
    public String addPostDel(@PathVariable(value = "id") long id) {
        Task task = taskRepository.findById((int) id).orElseThrow();
        logger.debug("Удаление задачи: {}", task.getName());
        taskRepository.delete(task);
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Имя пользователя
            logger.debug("Пользователь {} удалил задачу: {}, ID: {}", username, task.getName(), task.getId());
        }

        return redirect;
    }

    @GetMapping("/tasks/{id}")
    public String taskView(@PathVariable(value = "id") int id, Model model) {
        if (!taskRepository.existsById(id)) {
            return redirect;
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
            return redirect;
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
            return redirect + id;
        }
        // Получение информации о текущем пользователе
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Имя пользователя
            logger.info("Пользователь {} редактирует задачу: {}", username, task.getName());
        }

        if (!taskRepository.existsById(id)) {
            return redirect;
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

        return redirect;
    }
}
