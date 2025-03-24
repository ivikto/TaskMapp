package com.taskm.TaskMapp.repo;

import com.taskm.TaskMapp.model.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Integer> {
}
