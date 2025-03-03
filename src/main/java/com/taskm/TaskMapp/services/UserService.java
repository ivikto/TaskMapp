package com.taskm.TaskMapp.services;

import com.taskm.TaskMapp.model.User;
import com.taskm.TaskMapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Шифруем пароль
        user.setRoles(Collections.singleton("ROLE_USER")); // Устанавливаем роль по умолчанию
        userRepository.save(user);
    }
}
