package com.taskm.TaskMapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Title не может быть пустым")
    @Size(min = 5, max = 100, message = "Наименование должно содержать от 5 до 100 символов")
    private String title;
    @NotEmpty(message = "Content не может быть пустым")
    @Size(min = 5, max = 5000, message = "Описание должно содержать от 5 до 5000 символов")
    private String content;
    @NotEmpty(message = "Author не может быть пустым")
    private String author;
    private LocalTime date;
    @NotEmpty(message = "Link не может быть пустым")
    private String imageLink;

    public void setDate(LocalTime date) {
        this.date = date;
    }


    public void setViews(int views) {
        this.views = views;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int views;

    private int likes;

    @ElementCollection
    @CollectionTable(name = "news_liked_users", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "username")
    private Set<String> likedUsers = new HashSet<>();

    public News() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalTime getDate() {
        return date;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getViews() {
        return views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Set<String> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<String> likedUsers) {
        this.likedUsers = likedUsers;
    }

    // Дополнительные методы
    public boolean isLikedByUser(String username) {
        return likedUsers.contains(username);
    }

}



