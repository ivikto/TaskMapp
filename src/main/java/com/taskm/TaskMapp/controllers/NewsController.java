package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.Bot;
import com.taskm.TaskMapp.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.taskm.TaskMapp.repo.NewsRepository;
import com.taskm.TaskMapp.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalTime;
import java.util.*;

@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    private static final String redirect = "redirect:/news";

    private static final Logger logger = LoggerFactory.getLogger(TasksController.class);
    //private static final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @GetMapping("/news")
    public String news(Model model) {

        try {
            Iterable<News> news = newsRepository.findAll();
            model.addAttribute("news", news);
            model.addAttribute("emptyNews", new News());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "news";
    }

    @GetMapping("/news/{id}")
    public String taskView(@PathVariable(value = "id") int id, Model model) {
        if (!newsRepository.existsById(id)) {
            return redirect;
        }
        News news = newsRepository.findById(id).orElseThrow();
        model.addAttribute("news", news);

        return "new";
    }

    @PostMapping("/news/add")
    public String newsAdd(@ModelAttribute("news") News news, RedirectAttributes redirectAttributes) {
        logger.info("Cоздание новости: {}", news.getTitle());

        LocalTime now = LocalTime.now();
        System.out.println(now);
        news.setDate(now);
        news.setLikes(0);
        news.setViews(0);
        news.setAuthor("Ivikto");

        try {
            newsRepository.save(news);
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

        String message = String.format("Создана новая новость: %s, Исполнитель: %s", news.getTitle(), news.getAuthor());
        Bot.sendMessage(message);
        return redirect;
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
