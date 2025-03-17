package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.model.User;
import com.taskm.TaskMapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Возвращает страницу регистрации
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);

        return "redirect:/login"; // Перенаправляет на страницу входа
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Возвращает страницу регистрации
    }
}