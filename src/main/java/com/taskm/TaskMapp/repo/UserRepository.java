package com.taskm.TaskMapp.repo;

import com.taskm.TaskMapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
