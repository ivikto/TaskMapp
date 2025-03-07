package com.taskm.TaskMapp.repo;

import com.taskm.TaskMapp.model.News;
import com.taskm.TaskMapp.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Integer> {
}
