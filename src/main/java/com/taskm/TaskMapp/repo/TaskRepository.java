package com.taskm.TaskMapp.repo;

import com.taskm.TaskMapp.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
