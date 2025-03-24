package com.taskm.TaskMapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 5, max = 50, message = "Наименование должно содержать от 5 до 50 символов")
    private String title;
    @NotEmpty
    @Size(min = 5, max = 1000, message = "Описание должно содержать от 5 до 50 символов")
    private String content;
    @NotEmpty
    private String author;
    @NotEmpty
    @NotNull(message = "Поле дата должно быть заполнено")
    private String date;
    @NotEmpty
    private String imageLink;

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

    public String getDate() {
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



