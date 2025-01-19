package com.snehasish.web.todo.repositories;

import com.snehasish.web.todo.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
