package com.taskm.TaskMapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }




}
