package com.snehasish.web.todo.services;

import com.snehasish.web.todo.dto.Response;
import com.snehasish.web.todo.dto.TaskDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface TaskService {
    Response<List<TaskDto>> getAllTasks();
    Response<TaskDto> getOneTask(@NotNull Long id);
    Response<TaskDto> changeTaskStatus(@NotNull Long id);
    Response<TaskDto> createTask(@Valid TaskDto taskDto);
    Response<TaskDto> deleteTask(@NotNull Long taskId);
}
