package com.taskm.TaskMapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Set;

@Entity
@Data
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @NotEmpty
    @Size(min = 3, max = 30, message = "Имя пользователя должно содержать от 3 до 30 символов")
    private String username;
    @Column(nullable = false)
    @NotEmpty
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 60, message = "Пароль должен быть от 8 до 20 символов")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Пароль должен содержать хотя бы одну цифру, одну заглавную букву, одну строчную букву и один специальный символ")
    private String password;
    private boolean enabled;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
