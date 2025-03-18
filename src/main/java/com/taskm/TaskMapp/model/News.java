package com.taskm.TaskMapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public News() {}

    public Long getId() {
        return id;
    }
}
