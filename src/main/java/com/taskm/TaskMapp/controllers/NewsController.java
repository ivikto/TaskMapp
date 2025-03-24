package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.News;
import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.repo.NewsRepository;
import com.taskm.TaskMapp.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class NewsController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/news")
    public String news(Model model) {

        try {
            Iterable<News> news = newsRepository.findAll();
            model.addAttribute("news", news);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "news";
    }

    @PostMapping("/news/{id}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeNews(
            @PathVariable int id,
            Principal principal,
            HttpServletRequest request
    ) {
        Map<String, Object> response = new HashMap<>();

        if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            News news = newsRepository.findById(id).orElseThrow();
            String username = principal != null ? principal.getName() : null;

            if (username == null) {
                response.put("success", false);
                response.put("message", "Требуется авторизация");
                return ResponseEntity.status(401).body(response);
            }

            Set<String> likedUsers = news.getLikedUsers();
            boolean isLiked = likedUsers.contains(username);

            if (isLiked) {
                likedUsers.remove(username);
                news.setLikes(Math.max(0, news.getLikes() - 1));
            } else {
                likedUsers.add(username);
                news.setLikes(news.getLikes() + 1);
            }

            newsRepository.save(news);

            response.put("success", true);
            response.put("isLiked", !isLiked);
            response.put("likes", news.getLikes());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка сервера");
            return ResponseEntity.status(500).body(response);
        }
    }
}
