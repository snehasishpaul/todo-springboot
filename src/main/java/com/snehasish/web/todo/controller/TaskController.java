package com.snehasish.web.todo.controller;

import com.snehasish.web.todo.dto.Response;
import com.snehasish.web.todo.dto.TaskDto;
import com.snehasish.web.todo.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/todos")
//@CrossOrigin(origins = "http://localhost:5173/")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //getting tasks
    @GetMapping
    public ResponseEntity<Response<List<TaskDto>>> getAllTasks() {
        log.info("...getAllTasks...");
        Response<List<TaskDto>> response = this.taskService.getAllTasks();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    //creating task
    @PostMapping
    public ResponseEntity<Response<TaskDto>> createTask(@Valid @RequestBody TaskDto taskDto) {
        log.info("...createTask...");
        Response<TaskDto> response = this.taskService.createTask(taskDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    //get one task
    @GetMapping("/{taskId}")
    public ResponseEntity<Response<TaskDto>> getOneTask(@NotNull @PathVariable Long taskId) {
        log.info("...getOneTask...");
        Response<TaskDto> response = this.taskService.getOneTask(taskId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    //update task
    //change task status
    @GetMapping("/{taskId}/changeTaskStatus")
    public ResponseEntity<Response<TaskDto>> changeTaskStatus(@NotNull @PathVariable Long taskId) {
        log.info("...changeTaskStatus...");
        Response<TaskDto> response = this.taskService.changeTaskStatus(taskId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    //delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Response<TaskDto>> deleteTask(@NotNull @PathVariable Long taskId) {
        log.info("...deleteTask...");
        Response<TaskDto> response = this.taskService.deleteTask(taskId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
