package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.News;
import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.repo.NewsRepository;
import com.taskm.TaskMapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    @Autowired
    private UserRepository userRepository;
    private NewsRepository newsRepository;

    @GetMapping("/news")
    public String news(Model model) {
        try {
            Iterable<News> news = newsRepository.findAll();
            model.addAttribute("news", news);
        } catch (Exception e) {

        }

        return "news";
    }
}
