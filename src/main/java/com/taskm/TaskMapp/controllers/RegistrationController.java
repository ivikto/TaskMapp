package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Возвращает страницу регистрации
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String phone) {
        userService.registerUser(username, password, email, phone);
        return "redirect:/login"; // Перенаправляет на страницу входа
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Возвращает страницу регистрации
    }
}