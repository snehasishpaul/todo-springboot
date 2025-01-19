package com.snehasish.web.todo.services.impl;

import com.snehasish.web.todo.dto.Response;
import com.snehasish.web.todo.dto.TaskDto;
import com.snehasish.web.todo.entities.Task;
import com.snehasish.web.todo.exceptions.ResourceNotFoundException;
import com.snehasish.web.todo.repositories.TaskRepository;
import com.snehasish.web.todo.services.TaskService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Response<List<TaskDto>> getAllTasks() {
        List<Task> tasks = this.taskRepository.findAll();

        List<TaskDto> list = tasks.stream()
                .map(task -> TaskDto.builder().id(task.getId()).title(task.getTitle()).completed(task.isCompleted()).build())
                .toList();
        if(!list.isEmpty()) {
            return Response.<List<TaskDto>>builder()
                    .content(list)
                    .message("Task list retrieved successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(LocalDateTime.now())
                    .build();
        } else {
            return Response.<List<TaskDto>>builder()
                    .content(list)
                    .message("No tasks found")
                    .httpStatus(HttpStatus.OK) // Return OK for empty list
                    .timestamp(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public Response<TaskDto> createTask(TaskDto taskDto) {
        Task task = Task.builder()
                .title(taskDto.getTitle())
                .completed(taskDto.isCompleted())
                .build();

        Task savedTask = taskRepository.save(task);
        TaskDto taskdto = TaskDto.builder().id(savedTask.getId()).title(savedTask.getTitle()).completed(savedTask.isCompleted()).build();

        if(taskdto == null) {
            return Response.<TaskDto>builder()
                    .content(null)
                    .message("No tasks found")
                    .httpStatus(HttpStatus.OK) // Return OK for empty list
                    .timestamp(LocalDateTime.now())
                    .build();
        }
        return Response.<TaskDto>builder()
                .content(taskdto)
                .message("Task created successfully")
                .httpStatus(HttpStatus.OK) // Return OK for empty list
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public Response<TaskDto> getOneTask(@NotNull Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));
        TaskDto taskdto = TaskDto.builder().id(task.getId()).title(task.getTitle()).completed(task.isCompleted()).build();
        return Response.<TaskDto>builder()
                .content(taskdto)
                .message("Task Found")
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public Response<TaskDto> changeTaskStatus(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));
        task.setCompleted(!task.isCompleted());
        Task savedtask = this.taskRepository.save(task);
        TaskDto taskdto = mapTaskToTaskDto(savedtask);
        return Response.<TaskDto>builder()
                .content(taskdto)
                .message("Task status changed")
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public Response<TaskDto> deleteTask(Long taskId) {
        this.taskRepository.deleteById(taskId);
        return Response.<TaskDto>builder()
                .content(null)
                .message("Task deleted")
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private static TaskDto mapTaskToTaskDto(Task savedTask) {
        return TaskDto.builder().id(savedTask.getId()).title(savedTask.getTitle()).completed(savedTask.isCompleted()).build();
    }

}
